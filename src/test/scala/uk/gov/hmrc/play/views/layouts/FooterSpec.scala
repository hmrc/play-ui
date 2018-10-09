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

import org.scalatest.{Matchers, WordSpec}
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout}
import play.twirl.api.Html
import uk.gov.hmrc.play.test.StartedPlayApp
import uk.gov.hmrc.play.views
import uk.gov.hmrc.play.views.layouts.test.TestAssetsConfig

class FooterSpec extends WordSpec with Matchers with StartedPlayApp {
  "footer" should {
    "be renderable without a started Play application" in {
      thereShouldBeNoStartedPlayApp()

      val rendered = contentAsString(views.html.layouts.footer(
        analyticsToken = None,
        analyticsHost = "",
        ssoUrl = None,
        scriptElem = Some(Html("footer was rendered")),
        gaCalls = None)(TestAssetsConfig))

      rendered should include("footer was rendered")
    }

    "remove the query string by default from the page data item" in {
      val rendered = contentAsString(views.html.layouts.footer(
        analyticsToken = Some("TESTTOKEN"),
        analyticsHost = "localhost",
        ssoUrl = Some("localhost"),
        scriptElem = None,
        gaCalls = None)(TestAssetsConfig))

      rendered should include("ga('set',  'page', location.pathname);")
    }

    "include a ga script if an analyticsToken is supplied" in {
      val rendered = contentAsString(views.html.layouts.footer(
        analyticsToken = Some("GA-ANALYTICS"),
        ssoUrl = None,
        scriptElem = Some(Html("")),
        gaCalls = None)(TestAssetsConfig))

      rendered should include("analytics.js")
    }

    "allow the query string by exception in the page data item" in {
      val rendered = contentAsString(views.html.layouts.footer(
        analyticsToken = Some("TESTTOKEN"),
        analyticsHost = "localhost",
        ssoUrl = Some("localhost"),
        scriptElem = None,
        allowQueryStringInAnalytics = true,
        gaCalls = None)(TestAssetsConfig))

      rendered should not include("ga('set',  'page', location.pathname);")
    }
  }
}
