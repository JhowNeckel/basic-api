package br.com.neckel.application.usecase

import br.com.neckel.application.dto.{CreateUser, UserOutput}
import br.com.neckel.domain.model.User
import br.com.neckel.domain.repository.UserRepository

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

class UserService(repository: UserRepository) {

  def create(input: CreateUser)(implicit executor: ExecutionContext): Future[UserOutput] =
    input.name.trim match {
      case name if name.isEmpty => Future failed new IllegalStateException("user name must not be empty")
      case name =>
        val user = User(UUID.randomUUID(), name)
        repository.save(user).map(_ => UserOutput(user.id, user.name))
    }

  def get(id: UUID): Future[Option[User]] =
    repository.findById(id)

  def list(): Future[List[User]] =
    repository.listAll()

  def delete(id: UUID): Future[_] =
    repository.delete(id)

}
