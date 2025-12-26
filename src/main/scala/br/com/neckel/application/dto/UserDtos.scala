package br.com.neckel.application.dto

import java.util.UUID

sealed trait Input
case class CreateUser(name: String) extends Input

sealed trait Output
case class User(id: UUID, name: String) extends Output
