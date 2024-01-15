/*
 * Copyright 2023 HM Revenue & Customs
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
import play.api.i18n.Lang
import play.api.test.Helpers._
import uk.gov.hmrc.play.views.html.layouts.FooterLinks
import play.api.inject.guice.GuiceApplicationBuilder

import java.util.{List => JavaList}
import play.api.test.FakeRequest
import uk.gov.hmrc.play.MessagesSupport

import scala.collection.JavaConverters._
import scala.collection.immutable.List

class FooterLinksSpec extends WordSpec with Matchers with MessagesSupport {

  implicit val application =
    new GuiceApplicationBuilder()
      .configure(Map("play.i18n.langs" -> List("en", "cy")))
      .build()

  implicit val fakeRequest = FakeRequest("GET", "/foo")

  val englishLinkTextEntries: JavaList[String] = List(
    "Cookies",
    "Privacy policy",
    "Terms and conditions",
    "Help using GOV.UK"
  ).asJava

  val welshLinkTextEntries: JavaList[String] = List(
    "Cwcis",
    "Polisi preifatrwydd",
    "Telerau ac Amodau",
    "Help wrth ddefnyddio GOV.UK"
  ).asJava

  "The footerLinks in English" should {

    val footerLinks = application.injector.instanceOf[FooterLinks]

    val content  = contentAsString(footerLinks())
    val document = Jsoup.parse(content)
    val links    = document.getElementsByTag("a")

    "include visually hidden h2 text in English" in {
      val content = contentAsString(footerLinks())
      content should include("<h2 class=\"visually-hidden\">Support links</h2>")
    }

    "Have the correct link text in English" in {
      links.eachText() should be(englishLinkTextEntries)
    }

  }

  "The footerLinks in Welsh" should {

    val footerLinks = application.injector.instanceOf[FooterLinks]

    val welshMessages = messagesApi.preferred(Seq(Lang("cy")))
    val content       = contentAsString(footerLinks()(welshMessages, fakeRequest))
    val document      = Jsoup.parse(content)
    val links         = document.getElementsByTag("a")

    "include visually hidden h2 text in Welsh" in {
      content should include("<h2 class=\"visually-hidden\">Cysylltiadau cymorth</h2>")
    }

    "Have the correct link text in Welsh" in {
      links.eachText() should be(welshLinkTextEntries)
    }

  }
}
