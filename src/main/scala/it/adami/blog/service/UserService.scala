package it.adami.blog.service

import akka.actor.typed.ActorRef
import akka.cluster.sharding.typed.ShardingEnvelope
import it.adami.blog.actor.akka.command.UserCommand
import it.adami.blog.command.UserCommand.CreateUserCommand
import it.adami.blog.model.{GenericError, UserId}

import scala.concurrent.Future

class UserService(userRegion: ActorRef[ShardingEnvelope[UserCommand]]) {

  def createUser(cmd: CreateUserCommand): Future[Either[GenericError, UserId]] = {

    val akkaCommand = UserCommand.CreateUserCommand(
      username = cmd.userName,
      firstName = cmd.firstName,
      lastName = cmd.lastName,
      email = cmd.email,
      password = cmd.password,
      dateOfBirth = cmd.dateOfBirth,
      gender = cmd.gender
    )
    userRegion ! ShardingEnvelope(cmd.userName, akkaCommand)
    Future.successful(Right(UserId(cmd.userName)))

  }

}
