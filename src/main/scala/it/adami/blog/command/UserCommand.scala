package it.adami.blog.command

import java.util.Date

sealed trait UserCommand {}

object UserCommand {
  case class CreateUserCommand(
                                userName: String,
                                firstName: String,
                                lastName: String,
                                email: String,
                                password: String,
                                dateOfBirth: Date,
                                gender: String
  ) extends UserCommand
}
