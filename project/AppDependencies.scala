import play.core.PlayVersion
import sbt._

object AppDependencies {

  val compile = Seq(
    "com.typesafe.play" %% "play"            % PlayVersion.current,
    "com.typesafe.play" %% "filters-helpers" % PlayVersion.current,
    "org.joda"          % "joda-convert"     % "2.0.2"
  )

  val test = Seq(
    "org.scalatest"     %% "scalatest" % "3.0.5"             % Test,
    "org.pegdown"       % "pegdown"    % "1.6.0"             % Test,
    "org.jsoup"         % "jsoup"      % "1.11.3"            % Test,
    "com.typesafe.play" %% "play-test" % PlayVersion.current % Test
  )

}
