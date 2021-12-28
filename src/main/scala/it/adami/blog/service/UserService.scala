package it.adami.blog.service

import it.adami.blog.command.UserCommand.CreateUserCommand
import it.adami.blog.model.{GenericError, UserId}

class UserService {

  def createUser(cmd: CreateUserCommand): Either[GenericError, UserId] = ???

}
