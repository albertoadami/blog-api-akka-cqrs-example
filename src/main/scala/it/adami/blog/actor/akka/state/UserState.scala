package it.adami.blog.actor.akka.state

import java.time.LocalDateTime
import java.util.Date

final case class UserState(userName: String,
                           firstName: String,
                           lastName: String,
                           email: String,
                           password: String,
                           dateOfBirth: Date,
                           gender: String,
                           creationDate: LocalDateTime,
                           lastUpdateDate: Option[LocalDateTime],
                           active: Boolean)