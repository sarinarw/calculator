lazy val root = (project in file("."))
  .settings(
    name := "Calculator",
    scalaVersion := "2.12.7",
  )

libraryDependencies += "junit" % "junit" % "4.10" % Test
