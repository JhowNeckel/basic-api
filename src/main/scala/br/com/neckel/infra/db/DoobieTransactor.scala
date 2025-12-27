package br.com.neckel.infra.db

import cats.effect.IO
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import doobie.Transactor

import scala.concurrent.ExecutionContext

object DoobieTransactor {

  def xa(implicit ec: ExecutionContext): Transactor[IO] = {
    val cfg = new HikariConfig()

    cfg.setJdbcUrl("jdbc:sqlite:./data/app.db")
    cfg.setDriverClassName("org.sqlite.JDBC")
    cfg.setMaximumPoolSize(4)

    Transactor.fromDataSource[IO](new HikariDataSource(cfg), ec)
  }

}