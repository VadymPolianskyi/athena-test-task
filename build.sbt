name := "athena-test-task"

version := "0.1"

scalaVersion := "2.12.12"

val scalaLoggingVersion = "3.9.0"
val slf4jVersion = "2.0.0-alpha1"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.slf4j" % "slf4j-simple" % slf4jVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
  "org.scalatest" %% "scalatest" % "3.2.7" % Test,
  "org.scalamock" %% "scalamock" % "5.1.0" % Test
)