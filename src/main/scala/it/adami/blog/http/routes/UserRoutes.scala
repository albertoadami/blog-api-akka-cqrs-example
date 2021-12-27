package it.adami.blog.http.routes

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import it.adami.blog.http.json.CreateUserRequest
import it.adami.blog.http.validation.user.CreateUserValidation
import it.adami.blog.service.UserService

class UserRoutes(userService: UserService) extends GenericRoutes {

  val routes: Route =
    pathPrefix("users") {
      pathEnd {
        post {
          entity(as[CreateUserRequest]) { req =>

            val createUserValidation = CreateUserValidation(req)


            complete(StatusCodes.OK)
          }
        }
      }
    }
}
