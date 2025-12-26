package br.com.neckel.application.usecase

import br.com.neckel.application.dto.CreateUser
import br.com.neckel.infra.repository.FakeUserRepository
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.util.UUID
import scala.concurrent.ExecutionContext

class UserServiceSpec extends AnyWordSpec with Matchers with ScalaFutures {

  implicit val ec: ExecutionContext = ExecutionContext.global

  "UserService" should {
    "criar usuário quando o name é válido" in {
      val repo = new FakeUserRepository
      val service = new UserService(repo) // seu serviço real

      whenReady(service.create(CreateUser("Arthur"))) { user =>
        user.name shouldBe "Arthur"
      }
    }

    "rejeitar criação quando o name é vazio" in {
      val repo = new FakeUserRepository
      val service = new UserService(repo)

      whenReady(service.create(CreateUser("   ")).failed) { e =>
        e shouldBe a[IllegalStateException]
      }
    }

    "retornar None ao buscar usuário inexistente" in {
      val repo = new FakeUserRepository
      val service = new UserService(repo)

      whenReady(service.get(UUID.randomUUID())) { res =>
        res shouldBe None
      }
    }
  }

}
