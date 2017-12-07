/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.play.views.layouts

import org.jsoup.Jsoup
import org.scalatest.{Matchers, WordSpec}
import play.api.Configuration
import play.api.test.Helpers._
import uk.gov.hmrc.play.config.OptimizelyConfig
import uk.gov.hmrc.play.views.html.layouts.optimizelySnippet

import scala.collection.JavaConverters._

class OptimizelySnippetSpecs extends WordSpec with Matchers {

  "optimizelySnippet" should {

    "include script tag if projectId and baseUrl is defined" in new Setup {
      val baseUrl = "http://optimizely.com/"
      val projectId = "1234567"

      val config = configContaining(baseUrl = Some(baseUrl), projectId = Some(projectId))

      val document = Jsoup.parse(contentAsString(optimizelySnippet(config)))

      val scripts = document.head().select("script").iterator().asScala.toList.map(_.attr("src"))
      scripts should contain(s"$baseUrl$projectId.js")
    }

    "not include script tag if projectId is not defined" in new Setup {
      val baseUrl = "http://optimizely.com/"

      val config = configContaining(baseUrl = Some(baseUrl), projectId = None)

      val document = Jsoup.parse(contentAsString(optimizelySnippet(config)))

      document.head().select("script").iterator().asScala.toList.map(_.attr("src")) shouldBe empty
    }

    "not include script tag if baseUrl is not defined" in new Setup {
      val projectId = "1234567"

      val config = configContaining(baseUrl = None, projectId = Some(projectId))

      val document = Jsoup.parse(contentAsString(optimizelySnippet(config)))

      document.head().select("script").iterator().asScala.toList.map(_.attr("src")) shouldBe empty
    }
  }

  private trait Setup {
    def configContaining(baseUrl: Option[String], projectId: Option[String]): OptimizelyConfig = new OptimizelyConfig {
      override protected def configuration: Configuration = Configuration.from(
        ((baseUrl map (url => "optimizely.url" -> url)) :: (projectId map (id => "optimizely.projectId" -> id)) :: Nil).flatten.toMap
      )
    }
  }
}
