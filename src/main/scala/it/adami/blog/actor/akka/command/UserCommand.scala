package it.adami.blog.actor.akka.command

import java.time.LocalDate

import it.adami.blog.actor.akka.CborSerializable
import it.adami.blog.model.Gender

sealed trait UserCommand extends CborSerializable

object UserCommand {
  case class CreateUserCommand(
      username: String,
      firstName: String,
      lastName: String,
      email: String,
      password: String,
      dateOfBirth: LocalDate,
      gender: Gender
  ) extends UserCommand
}
