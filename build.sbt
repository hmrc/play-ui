import uk.gov.hmrc.playcrosscompilation.PlayVersion

val scala2_12 = "2.12.15"
val scala2_13 = "2.13.7"

val appName         = "play-ui"
val silencerVersion = "1.7.7"

lazy val root = Project(appName, file("."))
  .enablePlugins(SbtTwirl)
  .disablePlugins(JUnitXmlReportPlugin) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    majorVersion := 9,
    scalaVersion := scala2_12,
    crossScalaVersions := Seq(scala2_12, scala2_13),
    libraryDependencies ++= LibDependencies.libDependencies
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
    isPublicArtefact := true,
    // ***************
    // Use the silencer plugin to suppress warnings from unused imports in compiled twirl templates
    scalacOptions += "-P:silencer:pathFilters=views;routes",
    libraryDependencies ++= Seq(
      compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    ),
    excludeFilter in unmanagedSources := {
      if (PlayCrossCompilation.playVersion == PlayVersion.Play28)
        "deprecatedPlay26Helpers.scala" || "BackwardsCompatibilityDIAndStaticSpec.scala"
      else ""
    }
    // ***************
  )
  .settings(TwirlKeys.constructorAnnotations += "@javax.inject.Inject()")
  .settings(unmanagedSourceDirectories in sbt.Compile += baseDirectory.value / "src/main/twirl")
  .settings(parallelExecution in sbt.Test := false)
