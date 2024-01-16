/*
 * Copyright 2024 HM Revenue & Customs
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
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.views.html.layouts.BetaBanner

class BetaBannerSpec extends WordSpec with Matchers with MessagesSupport {

  "The BetaBanner" should {
    "include correct banner text" in {

      val sResult  = new BetaBanner()(true, "", "", true, false)
      val content  = contentAsString(sResult)
      val document = Jsoup.parse(content)

      document
        .getElementsByClass("beta-banner")
        .text shouldBe "BETA This is a new service – your feedback will help us to improve it."
    }
  }
}
