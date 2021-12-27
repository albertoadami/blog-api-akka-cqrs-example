package it.adami.blog.http.validation.user

import it.adami.blog.http.json.CreateUserRequest
import cats.implicits._

object CreateUserValidation extends UserValidator {

  def apply(req: CreateUserRequest): ValidationResult[CreateUserRequest] =
    (
      validateFirstName(req.firstname),
      validateLastName(req.lastname),
      validateEmail(req.email),
      validatePassword(req.password),
      validateBirthDate(req.dateOfBirth),
      validateGender(req.gender)
      ).mapN(CreateUserRequest.apply)

}