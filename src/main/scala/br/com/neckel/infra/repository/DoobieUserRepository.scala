package br.com.neckel.infra.repository

import br.com.neckel.domain.model.User
import br.com.neckel.domain.repository.UserRepository
import br.com.neckel.infra.db.{DoobieTransactor, Migrations}
import doobie.implicits._
import cats._
import cats.effect.unsafe.IORuntime
import cats.implicits._
import doobie._

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class DoobieUserRepository(implicit executor: ExecutionContext, rt: IORuntime) extends UserRepository {

  private val db = DoobieTransactor

  Migrations.createUserTable.run.transact(db.xa).unsafeToFuture() onComplete {
    case Success(_) => println("migration create user table succeded")
    case Failure(e) => println("migration create user table failure", e.getMessage)
  }

  override def save(user: User): Future[Unit] =
    sql"""
      INSERT INTO users (id, name)
      VALUES (${user.id}, ${user.name})
      ON CONFLICT(id) DO UPDATE SET
        name  = excluded.name
    """.update.run.void.transact(db.xa).unsafeToFuture()

  override def delete(id: UUID): Future[Unit] =
    sql"""DELETE FROM users WHERE id = $id""".update.run.void.transact(db.xa).unsafeToFuture()

  override def findById(id: UUID): Future[Option[User]] =
    sql"""SELECT id, name FROM users WHERE id = $id""".query[User].option.transact(db.xa).unsafeToFuture()

  override def listAll(): Future[List[User]] =
    sql"""SELECT id, name FROM users""".query[User].to[List].transact(db.xa).unsafeToFuture()

  private implicit val uuidMeta: Meta[UUID] =
    Meta[String].imap(UUID.fromString)(_.toString)
}
