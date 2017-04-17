/*
 * Copyright 2015 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import play.twirl.sbt.Import.TwirlKeys
import play.twirl.sbt.SbtTwirl
import sbt.Keys._
import sbt.{Build, _}
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

object HmrcBuild extends Build {

  import Dependencies._

  val appName = "play-ui"

  val appDependencies = Seq(
    Compile.play,
    Compile.playFilters,

    Test.scalaTest,
    Test.pegdown,
    Test.jsoup,
    Test.playTest,
	
	"uk.gov.hmrc" %% "emailaddress" % "0.2.0",
	"uk.gov.hmrc" %% "domain" % "2.11.0"
  )

  lazy val playUi = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl)
    .settings(
      name := appName,
      scalaVersion := "2.11.7",
      libraryDependencies ++= appDependencies,
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      crossScalaVersions := Seq("2.11.7")
    )
    .settings(TwirlKeys.templateImports ++= Seq("play.api.mvc._", "play.api.data._", "play.api.i18n._", "play.api.templates.PlayMagic._"))
    .settings(unmanagedSourceDirectories in sbt.Compile += baseDirectory.value / "src/main/twirl")
    .settings(parallelExecution in sbt.Test := false)
}

object Dependencies {
  import _root_.play.core.PlayVersion
  object Compile {
    val play = "com.typesafe.play" %% "play" % PlayVersion.current % "provided"
    val playFilters = "com.typesafe.play" %% "filters-helpers" % PlayVersion.current % "provided"
  }

  sealed abstract class Test(scope: String) {
    val scalaTest = "org.scalatest" %% "scalatest" % "2.2.4" % scope
    val pegdown = "org.pegdown" % "pegdown" % "1.6.0" % scope
    val jsoup = "org.jsoup" % "jsoup" % "1.7.2" % scope
    val playTest = "com.typesafe.play" %% "play-test" % PlayVersion.current % scope
  }

  object Test extends Test("test")
}
