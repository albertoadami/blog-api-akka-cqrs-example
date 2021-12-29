package it.adami.blog.http.validation.user

import it.adami.blog.http.json.CreateUserRequest
import cats.implicits._
import it.adami.blog.command.UserCommand.CreateUserCommand

object CreateUserValidation extends UserValidator {

  def apply(req: CreateUserRequest): ValidationResult[CreateUserCommand] =
    (
      validateUserName(req.username),
      validateFirstName(req.firstname),
      validateLastName(req.lastname),
      validateEmail(req.email),
      validatePassword(req.password),
      validateBirthDate(req.dateOfBirth),
      validateGender(req.gender)
    ).mapN(CreateUserCommand.apply)

}
