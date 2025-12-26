import sbt.*

object Dependencies {

  object Versions {
    val akka = "2.10.11"
    val akkaHttp = "10.7.3"
    val jackson = "2.20.1"
    val scalatest = "3.2.19"
  }

  val akka = "com.typesafe.akka" %% "akka-actor-typed" % Versions.akka
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp
  val akkaStreams = "com.typesafe.akka" %% "akka-stream" % Versions.akka
  val akkaStreamsTyped = "com.typesafe.akka" %% "akka-stream-typed" % Versions.akka
  val jackson = "com.typesafe.akka" %% "akka-http-jackson" % Versions.akkaHttp
  val jacksonScala = "com.fasterxml.jackson.module" %% "jackson-module-scala" % Versions.jackson
  val jacksonJavaTime = "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % Versions.jackson
  val scalatest = "org.scalatest" %% "scalatest" % Versions.scalatest % Test
  val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % Versions.akkaHttp % Test

}
