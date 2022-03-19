package it.adami.blog.http.routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import cats.data.Validated.{Invalid, Valid}
import it.adami.blog.http.json.{CreateUserRequest, UserCratedResponse}
import it.adami.blog.http.validation.user.CreateUserValidation
import it.adami.blog.service.UserService
import it.adami.blog.service.model.CreateUserError.UserNameAlreadyInUseError

import scala.concurrent.ExecutionContext

class UserRoutes(userService: UserService)(implicit val executionContext: ExecutionContext)
    extends GenericRoutes {

  private val createUserRoute: Route = path("users") {
    pathEnd {
      post {
        entity(as[CreateUserRequest]) { req =>
          CreateUserValidation(req) match {
            case Valid(cmd) =>
              onSuccess(userService.createUser(cmd)) {
                case Right(userId) =>
                  logger.info(s"Crated user with identifier $userId")
                  complete(StatusCodes.Created -> UserCratedResponse(userId.value))
                case Left(error) =>
                  error match {
                    case _: UserNameAlreadyInUseError =>
                      logger.info(s"Received error $error for user ${cmd.userName}")
                      complete(StatusCodes.Conflict)
                  }
              }
            case Invalid(errors) =>
              complete(StatusCodes.BadRequest -> buildErrorsResponse(errors))
          }
        }
      }
    }
  }

  val routes: Route = createUserRoute
}
