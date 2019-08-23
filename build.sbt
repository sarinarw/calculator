lazy val root = (project in file("."))
  .settings(
    name := "Calculator",
    scalaVersion := "2.12.7",
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.10" % Test,
      "com.novocode" % "junit-interface" % "0.11" % Test exclude("junit", "junit-dep")
    ),
    crossPaths := false,
  )



