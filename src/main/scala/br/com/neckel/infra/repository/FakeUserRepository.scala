package br.com.neckel.infra.repository

import br.com.neckel.domain.model.User
import br.com.neckel.domain.repository.UserRepository

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.CollectionConverters._

class FakeUserRepository()(implicit executor: ExecutionContext) extends UserRepository {
  private val db = new ConcurrentHashMap[UUID, User]()

  override def save(user: User): Future[_] =
    Future(db.put(user.id, user))

  override def findById(id: UUID): Future[Option[User]] =
    Future(Option(db.get(id)))

  override def listAll(): Future[List[User]] =
    Future(db.values().asScala.toList)

  override def delete(id: UUID): Future[_] =
    Future(db.remove(id))
}
