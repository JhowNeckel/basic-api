package br.com.neckel.domain.repository

import br.com.neckel.domain.model.User

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

trait UserRepository {
  def save(user: User): Future[Unit]
  def delete(id: UUID): Future[Unit]
  def findById(id: UUID): Future[Option[User]]
  def listAll(): Future[List[User]]
}
