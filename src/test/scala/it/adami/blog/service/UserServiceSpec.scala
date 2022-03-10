package it.adami.blog.service

import java.time.LocalDate

import akka.actor.testkit.typed.scaladsl
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.cluster.sharding.typed.ShardingEnvelope
import it.adami.blog.actor.akka.command.UserCommand
import it.adami.blog.actor.akka.result.{UserCreated, UsernameAlreadyInUse}
import it.adami.blog.command.UserCommand.CreateUserCommand
import it.adami.blog.common.SpecBase
import it.adami.blog.model.{Gender, UserId}
import it.adami.blog.service.model.CreateUserError
import org.scalatest.{BeforeAndAfterAll, EitherValues}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.duration._

class UserServiceSpec extends SpecBase with BeforeAndAfterAll with ScalaFutures with EitherValues {

  private val testKit = ActorTestKit()

  private implicit val timeout: FiniteDuration = 3 seconds

  override def afterAll(): Unit = testKit.shutdownTestKit()

  private class Fixture {
    val userRegionProbe: scaladsl.TestProbe[ShardingEnvelope[UserCommand]] =
      testKit.createTestProbe[ShardingEnvelope[UserCommand]]()
    val subject =
      new UserService(userRegionProbe.ref)(testKit.system, testKit.system.executionContext, timeout)

  }

  "UserService" when {
    "createUser(cmd) is called" must {
      "return Right(UserId) if the user is created correctly" in new Fixture {
        val createUser: CreateUserCommand                         = createUserCommand
        val futureResult: Future[Either[CreateUserError, UserId]] = subject.createUser(createUser)
        val actorResponse: UserCreated                            = UserCreated(UserId(createUser.userName))

        userRegionProbe.receiveMessage().message match {
          case cmd: UserCommand.CreateUserCommand => cmd.replyTo ! actorResponse
        }

        futureResult.futureValue mustBe Right(actorResponse.userId)
      }

      "return Left(UsernameAlreadyInUse) if the username already exists" in new Fixture {
        val createUser: CreateUserCommand                         = createUserCommand
        val futureResult: Future[Either[CreateUserError, UserId]] = subject.createUser(createUser)
        val actorResponse: UsernameAlreadyInUse                   = UsernameAlreadyInUse(UserId(createUser.userName))

        userRegionProbe.receiveMessage().message match {
          case cmd: UserCommand.CreateUserCommand => cmd.replyTo ! actorResponse
        }

        futureResult.futureValue mustBe Left(
          CreateUserError.UserNameAlreadyInUseError(createUser.userName)
        )
      }
    }
  }

  private def createUserCommand: CreateUserCommand =
    CreateUserCommand(
      userName = "user1",
      firstName = "name1",
      lastName = "lastname1",
      email = "test@test.it",
      password = "password1",
      dateOfBirth = LocalDate.now(),
      gender = Gender.Male
    )

}