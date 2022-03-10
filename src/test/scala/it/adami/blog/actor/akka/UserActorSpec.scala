package it.adami.blog.actor.akka

import java.time.{LocalDate, LocalDateTime}

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.ActorRef
import akka.persistence.testkit.scaladsl.EventSourcedBehaviorTestKit
import akka.persistence.typed.PersistenceId
import com.typesafe.config.ConfigFactory
import it.adami.blog.actor.akka.command.UserCommand
import it.adami.blog.actor.akka.command.UserCommand.CreateUserCommand
import it.adami.blog.actor.akka.event.UserEvent
import it.adami.blog.actor.akka.result.{UserCreated, UserResult, UsernameAlreadyInUse}
import it.adami.blog.actor.akka.state.{State, UserState}
import it.adami.blog.common.FixedClock
import it.adami.blog.model.{Gender, UserId}
import org.scalatest.{BeforeAndAfterEach, OptionValues}
import org.scalatest.wordspec.AnyWordSpecLike

class UserActorSpec
    extends ScalaTestWithActorTestKit(
      EventSourcedBehaviorTestKit.config.withFallback(ConfigFactory.load())
    )
    with AnyWordSpecLike
    with FixedClock
    with OptionValues
    with BeforeAndAfterEach {

  private val eventSourcedTestKit =
    EventSourcedBehaviorTestKit[UserCommand, UserEvent, State[UserState]](
      system,
      UserActor(PersistenceId("User", "test"))
    )

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    eventSourcedTestKit.clear()
  }

  "UserActor" when {

    "CreateUserCommand is received" must {
      "create a new user in disabled state when a CreateUserCommand is received" in {

        val expectedState = UserState(
          userName = "test",
          firstName = "test",
          lastName = "test",
          email = "test@test.it",
          password = "password",
          dateOfBirth = LocalDate.of(1990, 1, 1),
          gender = Gender.Male,
          creationDate = fixedDateTime.toLocalDate,
          lastUpdateDate = None,
          active = false
        )

        val result = eventSourcedTestKit.runCommand[UserResult](createUserCommand)
        result.event shouldBe a[UserEvent]
        result.stateOfType[State[UserState]].value.value shouldBe expectedState
        result.reply shouldBe UserCreated(UserId("test"))
      }
      "return an error message if the username is already in use" in {
        val firstCreationResult = eventSourcedTestKit.runCommand[UserResult](createUserCommand)
        firstCreationResult.event shouldBe a[UserEvent]
        firstCreationResult.reply shouldBe UserCreated(UserId("test"))

        val secondCreationResult = eventSourcedTestKit.runCommand[UserResult](createUserCommand)
        secondCreationResult.reply shouldBe UsernameAlreadyInUse(UserId("test"))

      }
    }
  }

  private def createUserCommand(replyTo: ActorRef[UserResult]) = CreateUserCommand(
    username = "test",
    firstName = "test",
    lastName = "test",
    email = "test@test.it",
    password = "password",
    dateOfBirth = LocalDate.of(1990, 1, 1),
    gender = Gender.Male,
    replyTo = replyTo
  )

  override protected lazy val fixedDateTime: LocalDateTime = LocalDateTime.now()
}
