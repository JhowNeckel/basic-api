package br.com.neckel.interface.http

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import br.com.neckel.application.usecase.UserService
import br.com.neckel.infra.repository.FakeUserRepository
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class UserRoutesSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {

  "UserRoutes" should {
    "criar usuário com sucesso" in {
      val repository = new FakeUserRepository()
      val service = new UserService(repository)
      val routes = new UserRoutes(service).routes
      val json = """{"name":"Arthur"}"""

      Post("/users", HttpEntity(ContentTypes.`application/json`, json)) ~> routes ~> check {
        status.intValue() shouldBe 201
      }
    }

    "falhar 400 quando create falha" in {
      val repository = new FakeUserRepository()
      val service = new UserService(repository)
      val routes = new UserRoutes(service).routes
      val json = """{"name":"  "}"""

      Post("/users", HttpEntity(ContentTypes.`application/json`, json)) ~> routes ~> check {
        status.intValue() shouldBe 400
      }
    }
  }

}
