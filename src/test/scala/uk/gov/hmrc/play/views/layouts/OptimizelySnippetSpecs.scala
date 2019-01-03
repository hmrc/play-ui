/*
 * Copyright 2019 HM Revenue & Customs
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
import uk.gov.hmrc.play.views.html.layouts.OptimizelySnippet

import scala.collection.JavaConverters._

class OptimizelySnippetSpecs extends WordSpec with Matchers {

  "optimizelySnippet" should {

    "include script tag if both project id and baseUrl are defined" in {
      val optimizelyBaseUrl   = "http://optimizely.com/"
      val optimizelyProjectId = "1234567"

      val snippet = createSnippet(Some(optimizelyBaseUrl), Some(optimizelyProjectId))

      scripts(snippet) should contain(s"$optimizelyBaseUrl$optimizelyProjectId.js")
    }

    "not include script tag if project id is not defined" in {
      val snippet = createSnippet(baseUrl = Some("base-url"))
      scripts(snippet) shouldBe empty
    }

    "not include script tag if baseUrl is not defined" in {
      val snippet = createSnippet(projectId = Some("id"))
      scripts(snippet) shouldBe empty
    }
  }

  private def createSnippet(baseUrl: Option[String] = None, projectId: Option[String] = None): OptimizelySnippet =
    new OptimizelySnippet(
      new OptimizelyConfig(Configuration(Seq(
        baseUrl.map("optimizely.url" -> _),
        projectId.map("optimizely.projectId" -> _)
      ).flatten: _*))
    )

  private def scripts(snippet: OptimizelySnippet): List[String] = {
    val content = contentAsString(snippet())
    val document = Jsoup.parse(content)
    document.head().select("script").iterator().asScala.toList.map(_.attr("src"))
  }

}
