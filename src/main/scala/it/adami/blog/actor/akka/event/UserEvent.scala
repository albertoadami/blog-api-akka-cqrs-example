package it.adami.blog.actor.akka.event

import java.time.{LocalDate, LocalDateTime}

sealed trait UserEvent

final case class CreatedUserEvent(
    userName: String,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    dateOfBirth: LocalDate,
    gender: String,
    creationDate: LocalDateTime
) extends UserEvent
