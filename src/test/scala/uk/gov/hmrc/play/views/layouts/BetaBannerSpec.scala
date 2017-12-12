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

import java.net.URLEncoder

import org.jsoup.Jsoup
import org.scalatest.{Matchers, WordSpec}
import play.api.i18n.{DefaultLangs, DefaultMessagesApi, Lang, Messages}
import play.api.test.Helpers._
import play.api.{Configuration, Environment}
import uk.gov.hmrc.play.views


class BetaBannerSpec extends WordSpec with Matchers {

  val configuration = Configuration.from(Map("play.i18n.langs" -> List("en", "cy"), "play.i18n.path" -> null))
  val messagesApi = new DefaultMessagesApi(Environment.simple(), configuration, new DefaultLangs(configuration))
  implicit val messages = Messages(Lang("en"), messagesApi)

  "The BetaBanner" should {
    "include correct banner text" in {
      val sResult = views.html.layouts.betaBanner(true, "", "", true, None, false)
      val content = contentAsString(sResult)
      val document = Jsoup.parse(content)

      document.getElementsByClass("beta-banner").text shouldBe "BETA This is a new service - your feedback will help us to improve it."
    }

    "has proper link if unauthenticated user" in {
      val sResult = views.html.layouts.betaBanner(false, "http://authenticated", "http://unauthenticated", true, None, false)
      val content = contentAsString(sResult)
      val document = Jsoup.parse(content)

      document.getElementById("feedback-link").attr("href") shouldBe "http://unauthenticated"
    }

    "has proper link if no 'back' button requested" in {
      val sResult = views.html.layouts.betaBanner(true, "http://authenticated", "http://unauthenticated", true, None, false)
      val content = contentAsString(sResult)
      val document = Jsoup.parse(content)

      document.getElementById("feedback-link").attr("href") shouldBe "http://authenticated"

     }

    "has proper link if 'back' button requested" in {
      val sResult = views.html.layouts.betaBanner(true, "http://authenticated", "http://unauthenticated", true, Some("http://return"), false)
      val content = contentAsString(sResult)
      val document = Jsoup.parse(content)

      document.getElementById("feedback-link").attr("href") shouldBe s"http://authenticated?backUrl=${URLEncoder.encode("http://return", "UTF-8")}"

    }
  }
}