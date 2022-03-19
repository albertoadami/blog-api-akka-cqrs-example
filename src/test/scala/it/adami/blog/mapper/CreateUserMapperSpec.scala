package it.adami.blog.mapper

import java.time.LocalDate
import java.util.UUID

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import it.adami.blog.actor.akka.result.UserResult
import it.adami.blog.command.UserCommand.CreateUserCommand
import it.adami.blog.common.SpecBase
import it.adami.blog.model.Gender
import it.adami.blog.actor.akka.command.UserCommand.{CreateUserCommand => AkkaCreateUserCommand}
import org.scalatest.BeforeAndAfterAll

class CreateUserMapperSpec extends SpecBase with BeforeAndAfterAll {

  private val testKit           = ActorTestKit()
  override def afterAll(): Unit = testKit.shutdownTestKit

  private val userResultProbe = testKit.createTestProbe[UserResult]

  private val createUserCommand = CreateUserCommand(
    userName = s"user-${UUID.randomUUID().toString}",
    firstName = s"name-${UUID.randomUUID().toString}",
    lastName = s"lastname-${UUID.randomUUID().toString}",
    email = "test@test.it",
    password = s"password-${UUID.randomUUID().toString}",
    dateOfBirth = LocalDate.now(),
    gender = Gender.Male
  )

  "CrateUserMapper" must {
    "map from a CreateUserCommand to a AkkaCreateUserCommand" in {
      val expectedResult = AkkaCreateUserCommand(
        username = createUserCommand.userName,
        firstName = createUserCommand.firstName,
        lastName = createUserCommand.lastName,
        email = createUserCommand.email,
        password = createUserCommand.password,
        dateOfBirth = createUserCommand.dateOfBirth,
        gender = createUserCommand.gender,
        replyTo = userResultProbe.ref
      )

      CreateUserMapper.createUserAkkaCommand(
        createUserCommand,
        userResultProbe.ref
      ) mustBe expectedResult
    }
  }

}
