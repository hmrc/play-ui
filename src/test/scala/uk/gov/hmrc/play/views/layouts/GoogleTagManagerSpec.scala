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
import uk.gov.hmrc.play.config.GoogleTagManagerConfig
import uk.gov.hmrc.play.test.StartedPlayApp
import uk.gov.hmrc.play.views
import uk.gov.hmrc.play.views.layouts.test.TestGoogleTagManagerConfig


class GoogleTagManagerSpec extends WordSpec with Matchers with StartedPlayApp {

  "script snippet" should {
    "be included when a Google Tag Manager container ID is present" in {
      val rendered = contentAsString(views.html.layouts.googleTagManager()(TestGoogleTagManagerConfig))
      rendered should include regex ".*'gtm\\.js'.*"
    }

    "" +: "N/A" +: Nil foreach { gtmContainerId =>
      s"not be included when a Google Tag Manager container ID is '$gtmContainerId'" in {
        val googleTagManagerConfig = new GoogleTagManagerConfig {
          override lazy val containerId: String = gtmContainerId
        }
        val rendered = contentAsString(views.html.layouts.googleTagManager()(googleTagManagerConfig))
        rendered should not include regex (".*'gtm\\.js'.*")
      }
    }
  }
}
