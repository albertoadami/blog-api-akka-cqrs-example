package it.adami.blog.model

trait GenericError {
  def message: String
  def messageDescription: String
}

final class UserNameAlreadyInUseError(userName: String) extends GenericError {
  override def message: String = "username already in use"

  override def messageDescription: String = s"the username $userName already exist in the system"
}
