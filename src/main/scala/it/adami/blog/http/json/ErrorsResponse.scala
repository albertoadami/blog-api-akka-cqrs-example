package it.adami.blog.http.json

case class ErrorsResponse(errors: List[ErrorItem])

object ErrorsResponse {
  def from(error: ErrorItem): ErrorsResponse = new ErrorsResponse(List(error))
}

case class ErrorItem(field: Option[String] = None, errorDescription: String)