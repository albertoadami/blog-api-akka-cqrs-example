package it.adami.blog.command

sealed trait UserCommand {}

object UserCommand {
  case class CreateUserCommand(
      firstName: String,
      lastName: String,
      email: String,
      password: String,
      dateOfBirth: String,
      gender: String
  ) extends UserCommand
}
