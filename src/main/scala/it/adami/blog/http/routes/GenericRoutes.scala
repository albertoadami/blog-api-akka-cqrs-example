package it.adami.blog.http.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Route
import it.adami.blog.http.JsonFormats

trait GenericRoutes extends JsonFormats with SprayJsonSupport {

  def routes: Route

}
