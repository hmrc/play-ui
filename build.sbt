val scala2_13 = "2.13.7"

val silencerVersion = "1.7.7"

lazy val root = Project("play-ui-play-29", file("."))
  .enablePlugins(SbtTwirl)
  .disablePlugins(JUnitXmlReportPlugin) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    majorVersion := 9,
    scalaVersion := scala2_13,
    crossScalaVersions := Seq(scala2_13),
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play"                 % "2.9.0",
      "com.typesafe.play" %% "play-filters-helpers" % "2.9.0",
      "joda-time"          % "joda-time"            % "2.12.5",
      "org.joda"           % "joda-convert"         % "2.0.2",
      "org.apache.commons" % "commons-text"         % "1.9",
      "org.scalatest"     %% "scalatest"            % "3.0.8"  % Test,
      "org.pegdown"        % "pegdown"              % "1.6.0"  % Test,
      "org.jsoup"          % "jsoup"                % "1.11.3" % Test,
      "com.typesafe.play" %% "play-test"            % "2.9.0"  % Test,
      "org.scalacheck"    %% "scalacheck"           % "1.14.0" % Test
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
    isPublicArtefact := true,
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
