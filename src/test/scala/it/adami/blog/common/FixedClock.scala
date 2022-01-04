package it.adami.blog.common

import java.time.{Clock, LocalDateTime, ZoneOffset}

trait FixedClock {

  protected def fixedDateTime: LocalDateTime
  implicit protected def fixedClock: Clock =
    Clock.fixed(fixedDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)

}
