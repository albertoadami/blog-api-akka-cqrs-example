package it.adami.blog.common

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.TestKit
import it.adami.blog.http.JsonFormats
import org.mockito.scalatest.IdiomaticMockito
import spray.json.DefaultJsonProtocol

trait HttpSpecBase
    extends SpecBase
    with ScalatestRouteTest
    with IdiomaticMockito
    with SprayJsonSupport
    with DefaultJsonProtocol
    with JsonFormats {}
