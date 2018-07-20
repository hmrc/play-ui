/*
 * Copyright 2018 HM Revenue & Customs
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
import play.api.test.Helpers._
import uk.gov.hmrc.play.views.html.layouts.optimizelySnippet

import scala.collection.JavaConverters._

class OptimizelySnippetSpecs extends WordSpec with Matchers {

  "optimizelySnippet" should {

    "include script tag if project id is defined" in {
      val optimizelyBaseUrl = "http://optimizely.com/"
      val optimizelyProjectId = "1234567"
      val content = contentAsString(optimizelySnippet(optimizelyBaseUrl, Some(optimizelyProjectId)))
      val document = Jsoup.parse(content)
      val scripts = document.head().select("script").iterator().asScala.toList.map(_.attr("src"))
      scripts should contain(s"$optimizelyBaseUrl$optimizelyProjectId.js")
    }

    "not include script tag if project id is not defined" in {
      val optimizelyBaseUrl = "http://optimizely.com/"
      val content = contentAsString(optimizelySnippet(optimizelyBaseUrl, None))
      val document = Jsoup.parse(content)
      val scripts = document.head().select("script").iterator().asScala.toList.map(_.attr("src"))
      scripts shouldBe empty
    }
  }
}
