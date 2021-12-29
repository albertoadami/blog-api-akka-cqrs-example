package it.adami.blog.actor.akka.command

import java.util.Date

sealed trait UserCommand

object UserCommand {
  case class CreateUserCommand(username: String,
                               firstName: String,
                               lastName: String,
                               email: String,
                               password: String,
                               dateOfBirth: Date,
                               gender: String
                              ) extends UserCommand
}