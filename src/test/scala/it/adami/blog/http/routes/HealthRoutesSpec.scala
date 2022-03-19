package it.adami.blog.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import it.adami.blog.common.HttpSpecBase

class HealthRoutesSpec extends HttpSpecBase {

  private val subject = new HealthRoutes

  "GET /health" must {
    "return NoContent when the service is up" in {
      Get("/health") ~> Route.seal(subject.routes) ~> check {
        status mustBe StatusCodes.NoContent
      }
    }
  }

}
