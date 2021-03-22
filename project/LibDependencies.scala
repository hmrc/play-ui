import sbt.{ModuleID, _}
import uk.gov.hmrc.playcrosscompilation.PlayVersion.{Play25, Play26, Play27}
import PlayCrossCompilation.dependencies

object LibDependencies {

  lazy val libDependencies: Seq[ModuleID] = dependencies(
    shared = {

      val playVersion = PlayCrossCompilation.playVersion match {
        case Play26 => "2.6.20"
        case Play27 => "2.7.4"
      }

      val compile = Seq(
        "com.typesafe.play" %% "play"            % playVersion,
        "com.typesafe.play" %% "filters-helpers" % playVersion,
        "org.joda"           % "joda-convert"    % "2.0.2"
      )

      val test = Seq(
        "org.scalatest"     %% "scalatest"  % "3.0.8",
        "org.pegdown"        % "pegdown"    % "1.6.0",
        "org.jsoup"          % "jsoup"      % "1.11.3",
        "com.typesafe.play" %% "play-test"  % playVersion,
        "org.scalacheck"    %% "scalacheck" % "1.14.0"
      ).map(_ % Test)

      compile ++ test
    },
    play26 = {
      val test = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"
      ).map(_ % Test)
      test
    },
    play27 = {
      val compile = Seq(
        // Removed from play 2.7 but required by play-ui
        "org.apache.commons" % "commons-lang3" % "3.8.1"
      )
      val test    = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"
      ).map(_ % Test)
      compile ++ test
    }
  )
}
