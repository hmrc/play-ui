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

import org.jsoup.Jsoup
import org.scalatest.{Matchers, WordSpec}
import play.api.i18n.Lang
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout}
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.views.html.layouts.EuExitLinks

import java.util.{List => JavaList}
import scala.collection.JavaConverters._
import scala.collection.immutable.List

class EuExitLinksSpec extends WordSpec with Matchers with MessagesSupport {

  val englishLinkTextEntries: JavaList[String] = List(
    "Prepare your business for the UK leaving the EU",
    "Prepare for EU Exit if you live in the UK",
    "Living in Europe after the UK leaves the EU",
    "Continue to live in the UK after it leaves the EU"
  ).asJava

  val welshLinkTextEntries: JavaList[String] = List(
    "Paratoi eich busnes ar gyfer y DU yn gadael yr UE (Saesneg yn unig)",
    "Paratoi ar gyfer Ymadael â’r UE os ydych yn byw yn y DU (Saesneg yn unig)",
    "Byw yn Ewrop ar ôl i’r DU adael yr UE (Saesneg yn unig)",
    "Parhau i fyw yn y DU ar ôl iddi adael yr UE (Saesneg yn unig)"
  ).asJava

  val eu_exit_links = new EuExitLinks()

  "Eu Exit Links on an English Language Page" should {
    val markup   = contentAsString(eu_exit_links())
    val document = Jsoup.parse(markup)
    val links    = document.getElementsByTag("a")

    "Include the section header" in {
      markup should include("<h2 class=\"heading-medium\">Prepare for EU Exit</h2>")
    }
    "Include four links" in {
      links.size should be(4)
    }
    "Have the correct link text in English" in {
      links.eachText() should be(englishLinkTextEntries)
    }
  }

  "Eu Exit Links on a Welsh Language Page" should {
    val welshMessages = messagesApi.preferred(Seq(Lang("cy")))
    val markup        = contentAsString(eu_exit_links()(welshMessages))
    val document      = Jsoup.parse(markup)
    val links         = document.getElementsByTag("a")

    "Include the section header" in {
      markup should include("<h2 class=\"heading-medium\">Paratoi ar gyfer Ymadael â’r UE (Saesneg yn unig)</h2>")
    }
    "Include four links" in {
      links.size should be(4)
    }
    "Have the correct link text in Welsh" in {
      links.eachText() should be(welshLinkTextEntries)
    }
  }
}
