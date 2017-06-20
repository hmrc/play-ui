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

import javax.inject.Inject

import org.jsoup.Jsoup
import org.scalatest.{Matchers, WordSpec}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.test.Helpers._
import uk.gov.hmrc.play.test.StartedPlayApp
import uk.gov.hmrc.play.views


class BetaBannerSpec @Inject()(val messagesApi: MessagesApi) extends WordSpec with I18nSupport  with Matchers with StartedPlayApp{

  "The BetaBanner" should {
    "include correct banner text" in {
      val sResult = views.html.layouts.betaBanner(true, "", "", true, false)
      val content = contentAsString(sResult)
      val document = Jsoup.parse(content)

      document.getElementsByClass("beta-banner").text shouldBe "BETA This is a new service - your feedback will help us to improve it."
    }
  }
}
