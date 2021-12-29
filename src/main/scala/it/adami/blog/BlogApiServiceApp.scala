package it.adami.blog

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.actor.typed.scaladsl.Behaviors
import akka.cluster.sharding.typed.ShardingEnvelope
import akka.cluster.sharding.typed.scaladsl.EntityTypeKey
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, Entity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.persistence.typed.PersistenceId
import com.typesafe.config.ConfigFactory
import it.adami.blog.actor.akka.UserBehavior
import it.adami.blog.actor.akka.command.UserCommand
import it.adami.blog.http.routes.{HealthRoutes, UserRoutes}
import it.adami.blog.service.UserService

import scala.concurrent.ExecutionContextExecutor
import scala.util.Failure
import scala.util.Success

object BlogApiServiceApp {

  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
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

    val UserTypeKey = EntityTypeKey[UserCommand]("User")
    val userShardRegion: ActorRef[ShardingEnvelope[UserCommand]] = sharding.init(Entity(typeKey = UserTypeKey) { entityContext =>
      UserBehavior(PersistenceId(entityContext.entityTypeKey.name, entityContext.entityId))
    })

    val userService = new UserService(userShardRegion)

    val healthRoutes = new HealthRoutes

    val userRoutes = new UserRoutes(userService)

    val allRoutes = Seq(
                    userRoutes
    ).map { item =>
      pathPrefix("api" / "rest" / "v1.0"){
        item.routes
      }
    }.reduceLeft(_ ~ _)

    startHttpServer(healthRoutes.routes ~ allRoutes)(system)

  }
}
