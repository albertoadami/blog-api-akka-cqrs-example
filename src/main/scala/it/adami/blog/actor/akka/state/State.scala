package it.adami.blog.actor.akka.state

final case class State[T](value: Option[T])
