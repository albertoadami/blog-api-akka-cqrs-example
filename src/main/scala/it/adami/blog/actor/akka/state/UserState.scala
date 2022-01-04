package it.adami.blog.actor.akka.state

import java.time.{LocalDate, LocalDateTime}

import it.adami.blog.model.Gender

final case class UserState(
    userName: String,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    dateOfBirth: LocalDate,
    gender: Gender,
    creationDate: LocalDateTime,
    lastUpdateDate: Option[LocalDateTime],
    active: Boolean
)
