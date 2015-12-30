val projectName = "ai"
name := projectName

version := "1.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= List(
	"org.scala-lang" % "scala-swing" % "2.11.0-M7",
	"org.reflections" % "reflections" % "0.9.10",
	"io.reactivex" % "rxscala_2.11" % "0.25.1",
	"com.github.tototoshi" %% "scala-csv" % "1.2.2",
	"com.jsuereth" %% "scala-arm" % "1.4",
	"com.github.skozlov" %% "commons-scala" % "0.1.1",
	"org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

assemblyOutputPath in assembly := new File(s"target/$projectName.jar")