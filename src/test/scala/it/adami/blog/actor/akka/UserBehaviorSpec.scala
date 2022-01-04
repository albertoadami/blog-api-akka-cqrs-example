package it.adami.blog.actor.akka

import java.time.{LocalDate, LocalDateTime}

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.persistence.testkit.scaladsl.EventSourcedBehaviorTestKit
import akka.persistence.typed.PersistenceId
import com.typesafe.config.ConfigFactory
import it.adami.blog.actor.akka.command.UserCommand
import it.adami.blog.actor.akka.command.UserCommand.CreateUserCommand
import it.adami.blog.actor.akka.event.UserEvent
import it.adami.blog.actor.akka.state.{State, UserState}
import it.adami.blog.common.FixedClock
import it.adami.blog.model.Gender
import org.scalatest.{BeforeAndAfterEach, OptionValues}
import org.scalatest.wordspec.AnyWordSpecLike

class UserBehaviorSpec
    extends ScalaTestWithActorTestKit(
      EventSourcedBehaviorTestKit.config.withFallback(ConfigFactory.load())
    )
    with AnyWordSpecLike
    with FixedClock
    with OptionValues
    with BeforeAndAfterEach {

  system.logConfiguration()

  private val eventSourcedTestKit =
    EventSourcedBehaviorTestKit[UserCommand, UserEvent, State[UserState]](
      system,
      UserBehavior(PersistenceId("User", "test"))
    )

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    eventSourcedTestKit.clear()
  }

  "UserBehavior" must {

    val createUserCommand = CreateUserCommand(
      username = "test",
      firstName = "test",
      lastName = "test",
      email = "test@test.it",
      password = "password",
      dateOfBirth = LocalDate.of(1990, 1, 1),
      gender = Gender.Male
    )

    "create a new user in disabled state when a CreateUserCommand is received" in {

      val expectedState = UserState(
        userName = createUserCommand.username,
        firstName = createUserCommand.firstName,
        lastName = createUserCommand.lastName,
        email = createUserCommand.email,
        password = createUserCommand.password,
        dateOfBirth = createUserCommand.dateOfBirth,
        gender = createUserCommand.gender,
        creationDate = fixedDateTime,
        lastUpdateDate = None,
        active = false
      )

      val result = eventSourcedTestKit.runCommand(createUserCommand)
      result.event shouldBe a[UserEvent]

      result.stateOfType[State[UserState]].value.value shouldBe expectedState

    }
  }

  override protected lazy val fixedDateTime: LocalDateTime = LocalDateTime.now()
}
