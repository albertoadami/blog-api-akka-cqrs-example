package it.adami.blog.http.validation.user

import java.time.LocalDate

import cats.data.Validated.Valid
import it.adami.blog.SpecBase
import it.adami.blog.command.UserCommand.CreateUserCommand
import it.adami.blog.http.json.CreateUserRequest

class CreateUserValidationSpec extends SpecBase {

  val request = CreateUserRequest(
    username = "albertoadami",
    firstname = "alberto",
    lastname = "adami",
    email = "alberto@adami.it",
    password = "Passw@rd!",
    dateOfBirth = "02-02-1993",
    gender = "MALE"
  )

  "CreateUserValidation" must {
    "return true when validate a correct CreateUserRequest" in {
      val expectedResponse = CreateUserCommand(
        userName = request.username,
        firstName = request.firstname,
        lastName = request.lastname,
        email = request.email,
        password = request.password,
        dateOfBirth = LocalDate.of(1993, 2, 2),
        gender = request.gender
      )
      val response = CreateUserValidation(request)
      response.isValid mustBe true
      response mustBe Valid(expectedResponse)
    }

    "return false when validate an invalid CreateUserRequest" in {
      CreateUserValidation(request.copy(firstname = "")).isValid mustBe false
      CreateUserValidation(request.copy(lastname = "")).isValid mustBe false
      CreateUserValidation(request.copy(gender = "WRONG")).isValid mustBe false

    }

  }

}
