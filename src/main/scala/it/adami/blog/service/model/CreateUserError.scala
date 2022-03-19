package it.adami.blog.service.model

import it.adami.blog.model.GenericError

sealed trait CreateUserError extends GenericError

object CreateUserError {

  final case class UserNameAlreadyInUseError(userName: String) extends CreateUserError {
    override def message: String = "username already in use"

    override def messageDescription: String = s"the username $userName already exist in the system"
  }

}
