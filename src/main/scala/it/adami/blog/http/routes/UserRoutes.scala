package it.adami.blog.http.routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Route}
import cats.data.Validated.{Invalid, Valid}
import it.adami.blog.http.json.CreateUserRequest
import it.adami.blog.http.validation.user.CreateUserValidation
import it.adami.blog.service.UserService

import scala.concurrent.{ExecutionContext}

class UserRoutes(userService: UserService)(implicit val executionContext: ExecutionContext) extends GenericRoutes {

  val routes: Route =
    path("users") {
      pathEnd {
        post {
          entity(as[CreateUserRequest]) { req =>
            CreateUserValidation(req) match {
              case Valid(cmd) =>
                onSuccess(userService.createUser(cmd)) {
                  case Right(userId) =>
                    complete(StatusCodes.Created)
                  case Left(errors) =>
                    complete(StatusCodes.Conflict)
                }
              case Invalid(errors) =>
                complete(StatusCodes.BadRequest -> buildErrorsResponse(errors))
            }

          }
        }
      }
    }
}
