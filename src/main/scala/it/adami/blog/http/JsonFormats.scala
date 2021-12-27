package it.adami.blog.http

import it.adami.blog.http.json.CreateUserRequest
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonFormats {
  import DefaultJsonProtocol._

  implicit val userJsonFormat: RootJsonFormat[CreateUserRequest] = jsonFormat6(CreateUserRequest)
}
