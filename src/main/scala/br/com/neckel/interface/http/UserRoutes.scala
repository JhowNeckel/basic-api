package br.com.neckel.interface.http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive1, Route}
import br.com.neckel.application.dto.CreateUser
import br.com.neckel.application.usecase.UserService
import br.com.neckel.interface.http.serialization.JsonSupport

import java.util.UUID
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

class UserRoutes(service: UserService)(implicit executor: ExecutionContext) extends JsonSupport {

  val routes: Route = pathPrefix("users") {
    concat(
      pathEndOrSingleSlash {
        concat(
          get {
            onComplete(service.list()) {
              case Success(users) => complete(StatusCodes.OK, users)
              case Failure(e) => complete(StatusCodes.BadRequest, e)
            }
          },
          post {
            entity(as[CreateUser]) { data =>
              onComplete(service.create(data)) {
                case Success(user) => complete(StatusCodes.Created, user)
                case Failure(e) => complete(StatusCodes.BadRequest, e)
              }
            }
          }
        )
      },
      path(Segment) { userId =>
        uuidFromString(userId) { uuid =>
          concat(
            get {
              onComplete(service.get(uuid)) {
                case Success(Some(user)) => complete(StatusCodes.OK, user)
                case Success(None) => complete(StatusCodes.NotFound)
                case Failure(e) => complete(StatusCodes.BadRequest, e)
              }
            },
            delete {
              onComplete(service.delete(uuid)) {
                case Success(_) => complete(StatusCodes.OK)
                case Failure(e) => complete(StatusCodes.BadRequest, e)
              }
            }
          )
        }
      }
    )
  }

  private def uuidFromString(id: String): Directive1[UUID] =
    Try(UUID.fromString(id)) match {
      case Success(uuid) => provide(uuid)
      case Failure(_) => complete(StatusCodes.BadRequest)
    }
}
