import sbt.{ModuleID, _}
import uk.gov.hmrc.playcrosscompilation.PlayVersion.{Play25, Play26}
import PlayCrossCompilation.dependencies

object LibDependencies {

  lazy val libDependencies: Seq[ModuleID] = dependencies(
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
    },
    play25 = {
      val test = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1"
      ).map(_ % Test)
      test
    },
    play26 = {
      val test = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"
      ).map(_ % Test)
      test
    }
  )

  lazy val overrides: Seq[ModuleID] = dependencies(
    play25 = Seq(
      "com.typesafe.play" %% "twirl-api" % "1.1.1"
    )
  )

}
