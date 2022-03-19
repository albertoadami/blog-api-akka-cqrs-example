package it.adami.blog.mapper

import akka.actor.typed.ActorRef
import it.adami.blog.command.UserCommand.CreateUserCommand
import it.adami.blog.actor.akka.command.UserCommand.{CreateUserCommand => AkkaCreateUserCommand}
import it.adami.blog.actor.akka.result.UserResult

object CreateUserMapper {

  def createUserAkkaCommand(
      cmd: CreateUserCommand,
      replyTo: ActorRef[UserResult]
  ): AkkaCreateUserCommand =
    AkkaCreateUserCommand(
      username = cmd.userName,
      firstName = cmd.firstName,
      lastName = cmd.lastName,
      email = cmd.email,
      password = cmd.password,
      dateOfBirth = cmd.dateOfBirth,
      gender = cmd.gender,
      replyTo = replyTo
    )

}
