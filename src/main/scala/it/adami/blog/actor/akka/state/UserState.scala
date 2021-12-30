package it.adami.blog.actor.akka.state

import java.time.{LocalDate, LocalDateTime}

final case class UserState(
    userName: String,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    dateOfBirth: LocalDate,
    gender: String,
    creationDate: LocalDateTime,
    lastUpdateDate: Option[LocalDateTime],
    active: Boolean
)
