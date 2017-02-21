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

import org.scalatest.{Matchers, WordSpec}
import play.api.Play
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout}
import uk.gov.hmrc.play.views
import uk.gov.hmrc.play.views.layouts.test.{TestAssetsConfig, TestOptimizelyConfig}

class TestWithoutPlayAppSpec extends WordSpec with Matchers {

  "Header and Footer templates that use Play configuration" should {
    "be renderable without a started Play application" in {
      val rendered = contentAsString(views.html.layouts.test_header_footer_includes()(TestAssetsConfig, TestOptimizelyConfig))

      thereShouldBeNoStartedPlayApp()

      rendered should include("head was rendered")
      rendered should include("footer was rendered")
    }
  }

  private def thereShouldBeNoStartedPlayApp() = {
    intercept[RuntimeException] {
      Play.current
    }.getMessage should include("no started application")
  }
}
