package br.com.neckel

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import br.com.neckel.application.usecase.UserService
import br.com.neckel.infra.repository.DoobieUserRepository
import br.com.neckel.interface.http.UserRoutes
import cats.effect.unsafe.IORuntime

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("clean-architecture-example")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val rt: IORuntime = IORuntime.global

  val userRepo = new DoobieUserRepository()
  val createUser = new UserService(userRepo)
  val routes = new UserRoutes(createUser).routes

  Http().newServerAt("0.0.0.0", 9000).bind(routes) onComplete {
    case Success(binding) => println(s"server started at ${binding.localAddress.getHostString}:${binding.localAddress.getPort}")
    case Failure(e) => println("failed to bind HTTP endpoint", e)
  }
}
