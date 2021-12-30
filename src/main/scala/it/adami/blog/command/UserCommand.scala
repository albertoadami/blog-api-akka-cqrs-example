package it.adami.blog.command

import java.time.LocalDate

sealed trait UserCommand {}

object UserCommand {
  case class CreateUserCommand(
      userName: String,
      firstName: String,
      lastName: String,
      email: String,
      password: String,
      dateOfBirth: LocalDate,
      gender: String
  ) extends UserCommand
}
