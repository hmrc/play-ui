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
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}
import play.api.Configuration
import play.api.test.Helpers._
import play.twirl.api.Html
import uk.gov.hmrc.play.config.GTMConfig
import uk.gov.hmrc.play.views.html.layouts.GTMSnippet
import scala.collection.JavaConverters._

class GTMSnippetSpec extends WordSpec with Matchers with PropertyChecks {
  "gtmSnippet" should {
    "include script tag with appropriate URL for container if gtm.container is defined" in {
      val transitionalUrl = "http://s3bucket/transitional/gtm.js"
      val mainUrl         = "http://s3bucket/main/gtm.js"
      val containersAndUrls =
        Table(
          ("container", "url"),
          ("transitional", transitionalUrl),
          ("main", mainUrl)
        )

      forAll(containersAndUrls) { (container: String, url: String) =>
        val snippet = createSnippet(Some(mainUrl), Some(transitionalUrl), Some(container))
        script(snippet()) should contain(s"$url")
      }
    }

    "not include script tag if gtm.container is not defined" in {
      val snippet = createSnippet(None, None, None)
      script(snippet()) shouldBe empty
    }
  }

  private def createSnippet(
    mainUrl: Option[String]         = None,
    transitionalUrl: Option[String] = None,
    container: Option[String]): GTMSnippet = {
    val config = new GTMConfig(
      Configuration(
        Seq(
          mainUrl.map("gtm.main.url"                 -> _),
          transitionalUrl.map("gtm.transitional.url" -> _),
          container.map("gtm.container"              -> _)).flatten: _*))
    new GTMSnippet(config)
  }

  private def script(html: Html): List[String] = {
    val content  = contentAsString(html)
    val document = Jsoup.parse(content)
    document
      .head()
      .select("script")
      .iterator()
      .asScala
      .toList
      .map(_.attr("src"))
  }
}
