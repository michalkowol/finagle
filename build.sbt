name := "sbt-template"
version in ThisBuild := "1.0.0"
scalaVersion in ThisBuild := "2.11.7"

incOptions := incOptions.value.withNameHashing(nameHashing = true)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-encoding", "UTF-8", "-Ywarn-adapted-args:false")

jacoco.settings
jacoco.thresholds in jacoco.Config := de.johoop.jacoco4sbt.Thresholds(instruction = 75, method = 75, branch = 45, complexity = 55, line = 85, clazz = 85)

Revolver.settings
//Revolver.enableDebugging(port = 5005, suspend = false)

val akkaVersion = "2.3.14"
val cascadeVersion = "0.5.1"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
libraryDependencies += "com.paypal" %% "cascade-common" % cascadeVersion
libraryDependencies += "com.paypal" %% "cascade-json" % cascadeVersion

libraryDependencies += "org.apache.thrift" % "libthrift" % "0.9.2"
libraryDependencies += "com.twitter" %% "scrooge-core" % "4.0.0"
libraryDependencies += "com.twitter" %% "finagle-thrift" % "6.28.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % "test"
libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test"
