package br.com.neckel.infra.db

import doobie.implicits.toSqlInterpolator
import doobie.util.update.Update0

object Migrations {

  val createUserTable: Update0 =
    sql"""
      CREATE TABLE IF NOT EXISTS users (
        id    TEXT PRIMARY KEY,
        name  TEXT NOT NULL
      )
    """.update

}
