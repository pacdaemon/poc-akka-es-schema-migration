name := "poc-akka-es-schema-migration"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.25"
libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.5.25"
libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.5.25"
libraryDependencies += "org.iq80.leveldb"  % "leveldb" % "0.12"
libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.25" % Test

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.28"


PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)