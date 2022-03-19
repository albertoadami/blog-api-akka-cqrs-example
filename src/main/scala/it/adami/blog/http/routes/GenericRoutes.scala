package it.adami.blog.http.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import cats.data.NonEmptyChain
import it.adami.blog.http.JsonFormats
import com.typesafe.scalalogging.LazyLogging
import it.adami.blog.http.json.{ErrorItem, ErrorsResponse}
import it.adami.blog.http.validation.DomainValidation
import spray.json._

trait GenericRoutes
    extends Directives
    with JsonFormats
    with SprayJsonSupport
    with DefaultJsonProtocol
    with LazyLogging {

  def routes: Route

  protected def buildErrorsResponse(errors: NonEmptyChain[DomainValidation]): ErrorsResponse = {
    val errorItems =
      errors.map(item => ErrorItem(Some(item.field), item.errorMessage)).toNonEmptyList.toList
    ErrorsResponse(errorItems)
  }

}
