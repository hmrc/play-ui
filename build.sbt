import uk.gov.hmrc.playcrosscompilation.PlayVersion.{Play25, Play26}
import PlayCrossCompilation.dependencies

val appName = "play-ui"

lazy val root = Project(appName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl, SbtArtifactory)
  .settings(
    majorVersion := 8,
    scalaVersion := "2.11.12",
    crossScalaVersions := List("2.11.12", "2.12.8"),
    libraryDependencies ++= appDependencies,
    dependencyOverrides ++= overrides,
    resolvers :=
      Seq(
        "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      )
  )
  .settings(
    TwirlKeys.templateImports := templateImports,
    PlayCrossCompilation.playCrossCompilationSettings,
    makePublicallyAvailableOnBintray := true
  )
  .settings(TwirlKeys.constructorAnnotations += "@javax.inject.Inject()")
  .settings(unmanagedSourceDirectories in sbt.Compile += baseDirectory.value / "src/main/twirl")
  .settings(parallelExecution in sbt.Test := false)

lazy val appDependencies: Seq[ModuleID] = dependencies(
  shared = {

    val playVersion = PlayCrossCompilation.playVersion match {
      case Play25 => "2.5.19"
      case Play26 => "2.6.20"
    }

    val compile = Seq(
      "com.typesafe.play" %% "play"            % playVersion,
      "com.typesafe.play" %% "filters-helpers" % playVersion,
      "org.joda"          % "joda-convert"     % "2.0.2"
    )

    val test = Seq(
      "org.scalatest"     %% "scalatest"  % "3.0.5",
      "org.pegdown"       % "pegdown"     % "1.6.0",
      "org.jsoup"         % "jsoup"       % "1.11.3",
      "com.typesafe.play" %% "play-test"  % playVersion,
      "org.scalacheck"    %% "scalacheck" % "1.14.0"
    ).map(_ % Test)

    compile ++ test
  }
)

lazy val overrides: Set[ModuleID] = dependencies(
  play25 = Seq(
    "com.typesafe.play" %% "twirl-api" % "1.1.1"
  )
).toSet

lazy val templateImports: Seq[String] = {

  val allImports = Seq(
    "_root_.play.twirl.api.Html",
    "_root_.play.twirl.api.JavaScript",
    "_root_.play.twirl.api.Txt",
    "_root_.play.twirl.api.Xml",
    "play.api.mvc._",
    "play.api.data._",
    "play.api.i18n._",
    "play.api.templates.PlayMagic._"
  )

  val specificImports = PlayCrossCompilation.playVersion match {
    case Play25 =>
      Seq(
        "_root_.play.twirl.api.TemplateMagic._"
      )
    case Play26 =>
      Seq(
        "_root_.play.twirl.api.TwirlFeatureImports._",
        "_root_.play.twirl.api.TwirlHelperImports._"
      )
  }

  allImports ++ specificImports
}
