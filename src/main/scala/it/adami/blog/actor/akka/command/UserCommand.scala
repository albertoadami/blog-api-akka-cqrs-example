package it.adami.blog.actor.akka.command

import java.time.LocalDate

sealed trait UserCommand

object UserCommand {
  case class CreateUserCommand(
      username: String,
      firstName: String,
      lastName: String,
      email: String,
      password: String,
      dateOfBirth: LocalDate,
      gender: String
  ) extends UserCommand
}
