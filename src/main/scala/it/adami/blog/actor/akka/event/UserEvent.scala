package it.adami.blog.actor.akka.event

import java.time.LocalDateTime
import java.util.Date

sealed trait UserEvent

final case class CreatedUserEvent(userName: String,
                                  firstName: String,
                                  lastName: String,
                                  email: String,
                                  password: String,
                                  dateOfBirth: Date,
                                  gender: String,
                                  creationDate: LocalDateTime) extends UserEvent
