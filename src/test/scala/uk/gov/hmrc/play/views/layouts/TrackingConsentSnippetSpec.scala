/*
 * Copyright 2020 HM Revenue & Customs
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
import org.jsoup.nodes.Element
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.Application
import play.api.test.Helpers._
import play.twirl.api.Html
import uk.gov.hmrc.play.views.html.layouts.trackingConsentSnippet

import scala.collection.JavaConverters._

class TrackingConsentSnippetSpec extends WordSpec with Matchers with GuiceOneAppPerSuite {
  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "play.allowGlobalApplication" -> "true",
          "optimizely.url"              -> "https://cdn.optimizely.com/",
          "optimizely.projectId"        -> "1234567",
          "gtm.container"               -> "d"
        ))
      .build()

  "TrackingConsentSnippet" should {
    "include the tracking consent script tag" in {
      val script = getScript(trackingConsentSnippet(None), 0)

      script.attr("src") should be("http://localhost:12345/tracking-consent/tracking/d.js")
    }

    "include the optimizely script tag" in {
      val script = getScript(trackingConsentSnippet(None), 1)

      script.attr("src") should be("https://cdn.optimizely.com/1234567.js")
    }

    "include the optimizely gtm script tag" in {
      val script = getScript(trackingConsentSnippet(None), 2)

      script.attr("src") should be("http://localhost:12345/tracking-consent/tracking/optimizely.js")
    }

    "include tracking consent script tag with a nonce if nonce is defined" in {
      val script = getScript(trackingConsentSnippet(Some("abcdefghij")), 0)

      script.attr("nonce") should be("abcdefghij")
    }

    "include optimizely script tag with a nonce if nonce is defined" in {
      val script = getScript(trackingConsentSnippet(Some("abcdefghij")), 1)

      script.attr("nonce") should be("abcdefghij")
    }

    "include optimizely gtm script tag with a nonce if nonce is defined" in {
      val script = getScript(trackingConsentSnippet(Some("abcdefghij")), 2)

      script.attr("nonce") should be("abcdefghij")
    }

    "not include script tags with any nonce attributes if nonce is not defined" in {
      getScripts(trackingConsentSnippet(None)).map(_.attr("nonce")) should be(Seq("", "", ""))
    }
  }

  private def getScripts(html: Html): Seq[Element] =
    Jsoup.parse(contentAsString(html)).head().select("script").iterator().asScala.toSeq

  private def getScript(html: Html, index: Int): Element =
    getScripts(html)(index)
}
