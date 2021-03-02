import uk.gov.hmrc.playcrosscompilation.PlayVersion.Play25

val appName         = "play-ui"
val silencerVersion = "1.4.4"

lazy val root = Project(appName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl)
  .disablePlugins(JUnitXmlReportPlugin) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    majorVersion := 9,
    scalaVersion := "2.12.10",
    crossScalaVersions := List("2.11.12", "2.12.10"),
    libraryDependencies ++= LibDependencies.libDependencies,
    resolvers :=
      Seq(
        "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
        "typesafe-releases" at "https://repo.typesafe.com/typesafe/releases/"
      )
  )
  .settings(
    TwirlKeys.templateImports := Seq(
      "_root_.play.twirl.api.Html",
      "_root_.play.twirl.api.JavaScript",
      "_root_.play.twirl.api.Txt",
      "_root_.play.twirl.api.Xml",
      "play.api.mvc._",
      "play.api.data._",
      "play.api.i18n._",
      "play.api.templates.PlayMagic._",
      "_root_.play.twirl.api.TwirlFeatureImports._",
      "_root_.play.twirl.api.TwirlHelperImports._"
    ),
    PlayCrossCompilation.playCrossCompilationSettings,
    makePublicallyAvailableOnBintray := true,
    // ***************
    // Use the silencer plugin to suppress warnings from unused imports in compiled twirl templates
    scalacOptions += "-P:silencer:pathFilters=views;routes",
    libraryDependencies ++= Seq(
      compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    )
    // ***************
  )
  .settings(TwirlKeys.constructorAnnotations += "@javax.inject.Inject()")
  .settings(unmanagedSourceDirectories in sbt.Compile += baseDirectory.value / "src/main/twirl")
  .settings(parallelExecution in sbt.Test := false)
