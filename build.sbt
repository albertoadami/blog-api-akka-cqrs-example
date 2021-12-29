lazy val akkaHttpVersion = "10.2.7"
lazy val akkaVersion    = "2.6.17"
lazy val slickVersion = "3.3.3"


lazy val commonSettings = Seq(
  organization    := "it.adami.blog",
  scalaVersion    := "2.12.6",
  scalacOptions += "-Ypartial-unification"
)

lazy val dockerSettings: Seq[Def.Setting[_]] = Seq(
  dockerBaseImage := "openjdk:8-slim",
  daemonUserUid in Docker := None,
  daemonUser in Docker    := "daemon",
  dockerExposedPorts := Seq(8080)
)

lazy val root = (project in file(".")).
  enablePlugins(DockerPlugin, JavaServerAppPackaging, BuildInfoPlugin).
  settings(
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
      //jdbc plugin
      "com.lightbend.akka" %% "akka-persistence-jdbc" % "5.0.4",
      "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "org.postgresql" % "postgresql" % "42.3.1",
      //cats
      "org.typelevel" %% "cats-core" % "2.3.0",
      //logging
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
      //testing
      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-persistence-testkit" % akkaVersion % Test,
      "org.scalatest"     %% "scalatest"                % "3.1.4"         % Test
    )
  )
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings
  )
