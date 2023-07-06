import sbtassembly.AssemblyPlugin.assemblySettings

assemblySettings
ThisBuild / version := "0.1.0-SNAPSHOT"

assembly / assemblyMergeStrategy := {
  case "reference.conf" => MergeStrategy.concat
  case PathList("META-INF", _@_*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}
ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file("."))
  .settings(
    name := "pps-pokearena",
    assembly / assemblyJarName := "pps-pokearena.jar",
    libraryDependencies ++= Dependencies.Gdx()
  )
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.16" % Test
)
val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)