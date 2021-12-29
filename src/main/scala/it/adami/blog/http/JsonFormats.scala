package it.adami.blog.http

import it.adami.blog.http.json.{CreateUserRequest, ErrorItem, ErrorsResponse}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonFormats {
  import DefaultJsonProtocol._

  implicit val userJsonFormat: RootJsonFormat[CreateUserRequest] = jsonFormat7(CreateUserRequest)
  implicit val ErrorItemJsonFormat: RootJsonFormat[ErrorItem] = jsonFormat2(ErrorItem)
  implicit val errorsResponseJsonFormat: RootJsonFormat[ErrorsResponse] = jsonFormat1(ErrorsResponse.apply)
}
