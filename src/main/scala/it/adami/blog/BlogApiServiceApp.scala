package it.adami.blog

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.cluster.sharding.typed.scaladsl.ClusterSharding
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.typesafe.config.ConfigFactory
import it.adami.blog.http.routes.UserRoutes

import scala.concurrent.ExecutionContextExecutor
import scala.util.Failure
import scala.util.Success

object BlogApiServiceApp {

  //#start-http-server
  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    // Akka HTTP still needs a classic ActorSystem to start
    import system.executionContext

    val futureBinding = Http().newServerAt("localhost", 8080).bind(routes)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }
  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.load()

    implicit val system: ActorSystem[Nothing]               = ActorSystem(Behaviors.empty, "blog-api-system")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val sharding = ClusterSharding(system)

    val userRoutes = new UserRoutes

    startHttpServer(userRoutes.routes)(system)

  }
}
