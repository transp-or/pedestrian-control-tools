// Organisation name
organization := "transpor.tools"

name := "user-equilibrium"

version := "0.1-SNAPSHOT"

//scalaVersion := "2.11.8"

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This program is written in Java, hence to force sbt to compile java code
autoScalaLibrary := false

libraryDependencies ++= Seq(
  "org.jgrapht" % "jgrapht-core" % "1.0.1"
)
