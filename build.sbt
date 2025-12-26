import com.typesafe.sbt.packager.MappingsHelper.directory

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.18"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(binSettings)
  .settings(dockerSettings)
  .settings(
    name := "basic-api",
    libraryDependencies ++= Seq(
      Dependencies.akka,
      Dependencies.akkaHttp,
      Dependencies.akkaStreams,
      Dependencies.akkaStreamsTyped,
      Dependencies.jackson,
      Dependencies.jacksonScala,
      Dependencies.jacksonJavaTime,
      Dependencies.scalatest,
      Dependencies.akkaHttpTestkit
    )
  )

lazy val binSettings = Seq(
  Compile / mainClass := Some("br.com.neckel.Main"),
  Universal / mappings ++= directory("project/ext/"),
  Universal / javaOptions ++= Seq("-Djava.net.preferIPv4Stack=true"),
  bashScriptExtraDefines ++= Seq(
    """addJava "-XX:HeapDumpPath=log/OOM-`date +%Y%m%d%H%M%S`.dump"""",
    """for PARAM in $CUSTOM_PARAMS; do addJava $PARAM; done"""
  )
)

lazy val dockerSettings = {
  val registry = sys.env.getOrElse("REGISTRY", "docker.io")
  val installLocation = "/opt/app"

  Seq(
    Docker / maintainer := "Jhonatan Neckel de Carvalho <neckel.carvalho@gmail.com.br>",
    Docker / packageName := name.value,
    Docker / version := version.value,
    Docker / defaultLinuxInstallLocation := installLocation,
    dockerRepository := Some(registry),
    dockerUpdateLatest := false,
    dockerBaseImage := "progmaq/alpine-jre-17",
    dockerEnvVars ++= Map("APP_HOME" -> s"$installLocation/", "APP_VERSION" -> version.value),
    dockerExposedPorts ++= Seq(9000, 9001),
  )
}