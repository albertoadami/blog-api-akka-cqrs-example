package it.adami.blog.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import it.adami.blog.http.json.CreateUserRequest

class UserRoutes extends GenericRoutes {

  val routes: Route =
    pathPrefix("users") {
      pathEnd {
        post {
          entity(as[CreateUserRequest]) { _ =>
            complete(StatusCodes.NotImplemented)
          }
        }
      }
    }
}
