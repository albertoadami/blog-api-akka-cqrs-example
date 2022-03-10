package it.adami.blog.service

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.cluster.sharding.typed.ShardingEnvelope
import it.adami.blog.actor.akka.command.UserCommand
import it.adami.blog.command.UserCommand.CreateUserCommand
import it.adami.blog.model.UserId
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout
import it.adami.blog.actor.akka.result.{UserCreated, UserResult, UsernameAlreadyInUse}
import it.adami.blog.mapper.CreateUserMapper
import it.adami.blog.service.model.CreateUserError

import scala.concurrent.{ExecutionContext, Future}

class UserService(userRegion: ActorRef[ShardingEnvelope[UserCommand]])(implicit
    val system: ActorSystem[_],
    val executionContext: ExecutionContext,
    val timeout: Timeout
) {

  def createUser(cmd: CreateUserCommand): Future[Either[CreateUserError, UserId]] = {
    val result: Future[UserResult] = userRegion.ask[UserResult](ref =>
      ShardingEnvelope(cmd.userName, CreateUserMapper.createUserAkkaCommand(cmd, ref))
    )
    result map {
      case UserCreated(userId) => Right(userId)
      case UsernameAlreadyInUse(userId) =>
        Left(CreateUserError.UserNameAlreadyInUseError(userId.value))
    }

  }

}
