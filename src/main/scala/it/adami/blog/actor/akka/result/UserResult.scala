package it.adami.blog.actor.akka.result

import it.adami.blog.actor.akka.CborSerializable
import it.adami.blog.model.UserId

trait UserResult extends CborSerializable

sealed trait CreateUserResult extends UserResult

case class UserCreated(userId: UserId)          extends CreateUserResult
case class UsernameAlreadyInUse(userId: UserId) extends CreateUserResult
