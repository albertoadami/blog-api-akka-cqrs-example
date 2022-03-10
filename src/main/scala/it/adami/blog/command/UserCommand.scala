package it.adami.blog.command

import java.time.LocalDate

import it.adami.blog.model.Gender

sealed trait UserCommand

object UserCommand {
  case class CreateUserCommand(
      userName: String,
      firstName: String,
      lastName: String,
      email: String,
      password: String,
      dateOfBirth: LocalDate,
      gender: Gender
  ) extends UserCommand
}
