enablePlugins(JavaAppPackaging)
mainClass in Global := Some("StartBot")

name := "akka-http-hipchat-bot"
organization := "org.kkanojia"
version := "0.1"
scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.4.12"
  val scalaTestV = "3.0.0"
  val Json4sVersion = "3.5.0"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-persistence" % akkaV,
    "com.typesafe.akka" %% "akka-slf4j" % akkaV,

    //Akka Http
    "com.typesafe.akka" %% "akka-http-core" % "2.4.11",
    "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
    "org.json4s" %% "json4s-native" % Json4sVersion,
    "org.json4s" %% "json4s-ext" % Json4sVersion,
    "de.heikoseeberger" %% "akka-http-json4s" % "1.10.1",

    "io.spray" %% "spray-client" % "1.3.1",

    //NLP
    "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0",
    "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0" classifier "models",

    //Tests
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "com.typesafe.akka" %% "akka-http-testkit" % "2.4.11" % "test",
    "org.scalatest" %% "scalatest" % scalaTestV % "test"
  )
}

Revolver.settings
