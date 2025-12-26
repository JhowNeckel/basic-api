package br.com.neckel.application.usecase

import br.com.neckel.application.dto.CreateUser
import br.com.neckel.domain.model.User
import br.com.neckel.domain.repository.UserRepository

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class UserService(repository: UserRepository) {

  def create(input: CreateUser)(implicit executor: ExecutionContext): Future[User] =
    input.name.trim match {
      case name if name.isEmpty => Future failed new IllegalStateException("user name must not be empty")
      case name =>
        val user = User(UUID.randomUUID(), name)

        repository.save(user).map(_ => user)
    }

  def get(id: UUID): Future[Option[User]] =
    repository.findById(id)

  def list(): Future[List[User]] =
    repository.listAll()

  def delete(id: UUID): Future[_] =
    repository.delete(id)

}
