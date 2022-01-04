package it.adami.blog.actor.akka.event

import java.time.{LocalDate, LocalDateTime}

import it.adami.blog.actor.akka.CborSerializable
import it.adami.blog.model.Gender

sealed trait UserEvent extends CborSerializable

final case class CreatedUserEvent(
    userName: String,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    dateOfBirth: LocalDate,
    gender: Gender,
    creationDate: LocalDateTime
) extends UserEvent
