val appName = "play-ui"

lazy val root = Project(appName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl)
  .settings(
    scalaVersion        := "2.11.7",
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    resolvers           :=
      Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      )
  )
  .settings(
    TwirlKeys.templateImports ++=
      Seq(
        "play.api.mvc._",
        "play.api.data._",
        "play.api.i18n._",
        "play.api.templates.PlayMagic._"
      )
  )
  .settings(TwirlKeys.constructorAnnotations += "@javax.inject.Inject()")
  .settings(unmanagedSourceDirectories in sbt.Compile += baseDirectory.value / "src/main/twirl")
  .settings(parallelExecution in sbt.Test := false)
