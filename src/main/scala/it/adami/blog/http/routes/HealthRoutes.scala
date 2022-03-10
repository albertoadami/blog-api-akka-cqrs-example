package it.adami.blog.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

class HealthRoutes extends GenericRoutes {
  override def routes: Route =
    path("health") {
      get {
        complete(StatusCodes.NoContent)
      }

    }
}
