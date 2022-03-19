package it.adami.blog.common

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

trait SpecBase extends AnyWordSpec with Matchers {
  protected implicit val timeout: FiniteDuration = 3 seconds

}
