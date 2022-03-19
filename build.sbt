lazy val akkaHttpVersion = "10.2.7"
lazy val akkaVersion    = "2.6.18"
lazy val mockitoScalaVersion = "1.16.49"


lazy val commonSettings = Seq(
  organization    := "it.adami.blog",
  scalaVersion    := "2.12.6",
  scalacOptions += "-Ypartial-unification",
  parallelExecution in ThisBuild := false
)

lazy val dockerSettings: Seq[Def.Setting[_]] = Seq(
  dockerBaseImage := "openjdk:12-slim",
  daemonUserUid in Docker := None,
  daemonUser in Docker    := "daemon",
  dockerExposedPorts := Seq(9000)
)

lazy val root = (project in file(".")).
  enablePlugins(DockerPlugin, JavaServerAppPackaging, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(dockerSettings: _*)
    .settings(
    commonSettings,
    name := "blog-api-akka-cqrs-example",
    libraryDependencies ++= Seq(
      //akka dependencies
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-typed"       % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-sharding-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-persistence-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-serialization-jackson" % akkaVersion,
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.11.4",
      "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
      //cats
      "org.typelevel" %% "cats-core" % "2.3.0",
      //logging
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
      //testing
      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-persistence-testkit" % akkaVersion % Test,
      "org.scalatest"     %% "scalatest"                % "3.2.10"         % Test,
      "org.mockito" %% "mockito-scala"                  % mockitoScalaVersion % Test,
      "org.mockito" %% "mockito-scala-scalatest"                  % mockitoScalaVersion % Test
    )
  )
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings
  )
