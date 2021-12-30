package it.adami.blog.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import it.adami.blog.HttpSpecBase
import it.adami.blog.http.json.{CreateUserRequest, ErrorItem, ErrorsResponse, UserCratedResponse}
import it.adami.blog.model.{UserId, UserNameAlreadyInUseError}
import it.adami.blog.service.UserService

import scala.concurrent.Future

class UserRoutesSpec extends HttpSpecBase {

  class Fixture {

    val userServiceMock: UserService = mock[UserService]
    val subject                      = new UserRoutes(userServiceMock)
  }

  "POST /users" must {
    val username = "aadami"
    val validCreateUserRequest =
      CreateUserRequest(
        username = username,
        firstname = "alberto",
        lastname = "adami",
        email = "alberto@adami.it",
        password = "password",
        dateOfBirth = "01-01-1990",
        gender = "MALE"
      )
    "return Created if the username is not in use" in new Fixture {
      userServiceMock.createUser(*) returns Future.successful(Right(UserId(username)))
      Post("/users", validCreateUserRequest) ~> Route.seal(subject.routes) ~> check {
        status mustBe StatusCodes.Created
        responseAs[UserCratedResponse] mustBe UserCratedResponse(username)
      }
    }

    "return Conflict if the username is already in use" in new Fixture {
      userServiceMock.createUser(*) returns Future.successful(
        Left(new UserNameAlreadyInUseError(username))
      )
      Post("/users", validCreateUserRequest) ~> Route.seal(subject.routes) ~> check {
        status mustBe StatusCodes.Conflict
      }

    }

    "return BadRequest if the request contains some validation error" in new Fixture {
      Post("/users", validCreateUserRequest.copy(username = "")) ~> Route.seal(
        subject.routes
      ) ~> check {
        val expectedResponse =
          ErrorsResponse.from(ErrorItem(Some("username"), "username cannot be empty"))
        status mustBe StatusCodes.BadRequest
        responseAs[ErrorsResponse] mustBe expectedResponse
      }
    }

  }

}
