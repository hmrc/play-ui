import PlayCrossCompilation.dependencies
import sbt.{ModuleID, _}
import uk.gov.hmrc.playcrosscompilation.PlayVersion.{Play26, Play27, Play28}

object LibDependencies {

  lazy val libDependencies: Seq[ModuleID] = dependencies(
    shared = {

      val playVersion = PlayCrossCompilation.playVersion match {
        case Play26 => "2.6.20"
        case Play27 => "2.7.4"
        case Play28 => "2.8.7"
      }

      val compile = Seq(
        "com.typesafe.play" %% "play"            % playVersion,
        "com.typesafe.play" %% "filters-helpers" % playVersion,
        "org.joda"           % "joda-convert"    % "2.0.2",
        "org.apache.commons" % "commons-text"    % "1.9"
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
      val test = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"
      ).map(_ % Test)
      test
    },
    play28 = {
      val test = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"
      ).map(_ % Test)
      test
    }
  )
}
