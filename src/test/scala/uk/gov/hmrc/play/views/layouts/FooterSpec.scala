/*
 * Copyright 2021 HM Revenue & Customs
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
import uk.gov.hmrc.play.test.NoStartedPlayApp
import uk.gov.hmrc.play.views.html.layouts.Footer
import uk.gov.hmrc.play.views.layouts.test.TestConfigs._

class FooterSpec extends WordSpec with Matchers with NoStartedPlayApp {
  "footer" should {

    val footer = new Footer(testAssetsConfig)

    "be renderable without a started Play application" in {
      thereShouldBeNoStartedPlayApp()

      val rendered = contentAsString(
        footer(
          analyticsToken = None,
          analyticsHost = "",
          ssoUrl = None,
          scriptElem = Some(Html("footer was rendered")),
          gaCalls = None
        )
      )

      rendered should include("footer was rendered")
    }
  }
}
