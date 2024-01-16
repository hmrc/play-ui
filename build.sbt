val scala2_13 = "2.13.12"

lazy val root = Project("play-ui-play-30", file("."))
  .enablePlugins(SbtTwirl)
  .disablePlugins(JUnitXmlReportPlugin) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    majorVersion := 11,
    scalaVersion := scala2_13,
    crossScalaVersions := Seq(scala2_13),
    libraryDependencies ++= Seq(
      "org.playframework" %% "play"                 % "3.0.0",
      "org.playframework" %% "play-filters-helpers" % "3.0.0",
      "joda-time"          % "joda-time"            % "2.12.5",
      "org.joda"           % "joda-convert"         % "2.0.2",
      "org.apache.commons" % "commons-text"         % "1.9",
      "org.scalatest"     %% "scalatest"            % "3.0.8"  % Test,
      "org.pegdown"        % "pegdown"              % "1.6.0"  % Test,
      "org.jsoup"          % "jsoup"                % "1.11.3" % Test,
      "org.playframework" %% "play-test"            % "3.0.0"  % Test,
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
    scalacOptions += "-Wconf:src=routes/.*:s",
    scalacOptions += "-Wconf:src=views/.*:s"
  )
  .settings(TwirlKeys.constructorAnnotations += "@javax.inject.Inject()")
  .settings(unmanagedSourceDirectories in sbt.Compile += baseDirectory.value / "src/main/twirl")
  .settings(parallelExecution in sbt.Test := false)
