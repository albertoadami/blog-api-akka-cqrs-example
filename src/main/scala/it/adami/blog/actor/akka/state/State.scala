package it.adami.blog.actor.akka.state

import it.adami.blog.actor.akka.CborSerializable

final case class State[T](value: Option[T]) extends CborSerializable
