package it.adami.blog.actor.akka.state

import java.time.LocalDate

import it.adami.blog.model.Gender

final case class UserState(
    userName: String,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    dateOfBirth: LocalDate,
    gender: Gender,
    creationDate: LocalDate,
    lastUpdateDate: Option[LocalDate],
    active: Boolean
)
