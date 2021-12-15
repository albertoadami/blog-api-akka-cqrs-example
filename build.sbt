lazy val akkaHttpVersion = "10.2.7"
lazy val akkaVersion    = "2.6.17"

lazy val root = (project in file(".")).
  enablePlugins(DockerPlugin).
  settings(
    inThisBuild(List(
      organization    := "it.adami.blog",
      scalaVersion    := "2.13.4"
    )),
    name := "blog-api-akka-cqrs-example",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-typed"       % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-sharding-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-persistence-typed" % akkaVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",
      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-persistence-testkit" % akkaVersion % Test,
      "org.scalatest"     %% "scalatest"                % "3.1.4"         % Test
    )
  )
