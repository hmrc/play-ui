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

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{Lang, Messages, MessagesApi}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.{Call, PathBindable}
import play.api.test.Helpers._
import uk.gov.hmrc.play.views.html.layouts.LanguageSelection

import java.util.Locale

class LanguageSelectionSpec extends PlaySpec with GuiceOneAppPerSuite {
  override implicit lazy val app: Application = new GuiceApplicationBuilder()
    .configure(Map("play.i18n.langs" -> Seq("en", "cy", "es")))
    .build()
  val messagesApi: MessagesApi                = app.injector.instanceOf[MessagesApi]
  val messagesEnglish: Messages               = messagesApi.preferred(Seq(Lang(new Locale("en"))))
  val messagesWelsh: Messages                 = messagesApi.preferred(Seq(Lang(new Locale("cy"))))
  val messagesSpanish: Messages               = messagesApi.preferred(Seq(Lang(new Locale("es"))))
  val EnglishLangCode                         = "en"
  val WelshLangCode                           = "cy"

  val English: Lang = Lang(EnglishLangCode)
  val Welsh: Lang   = Lang(WelshLangCode)

  def languageMap: Map[String, Lang] = Map("english" -> English, "cymraeg" -> Welsh)
  def langToUrl(lang: String): Call  = Call("GET", "/language/" + implicitly[PathBindable[String]].unbind("lang", lang))

  "Language selection template view" should {

    "give a link to switch to Welsh when current language is English" in {
      val html = LanguageSelection.render(languageMap, langToUrl, None, None, messagesEnglish)
      contentType(html)     must be("text/html")
      contentAsString(html) must include(messagesEnglish("id=\"cymraeg-switch\""))
      contentAsString(html) must include("/language/cymraeg")
    }

    "show correct current language message when current language is English" in {
      val html = LanguageSelection.render(languageMap, langToUrl, None, None, messagesEnglish)
      contentType(html)     must be("text/html")
      contentAsString(html) must include("<span aria-current=\"true\">English</span>")
    }

    "give a link to switch to English when current language is Welsh" in {
      val html = LanguageSelection.render(languageMap, langToUrl, None, None, messagesWelsh)
      contentType(html)     must be("text/html")
      contentAsString(html) must include(messagesEnglish("id=\"english-switch\""))
      contentAsString(html) must include("/language/english")
    }

    "show correct current language message when current language is Welsh" in {
      val html = LanguageSelection.render(languageMap, langToUrl, None, None, messagesWelsh)
      contentType(html)     must be("text/html")
      contentAsString(html) must include("<span aria-current=\"true\">Cymraeg</span>")
    }

    "show a custom class if it is set" in {
      val html = LanguageSelection.render(languageMap, langToUrl, Some("float--right"), None, messagesWelsh)
      contentType(html)     must be("text/html")
      contentAsString(html) must include("class=\"float--right")
    }

    "show a data-journey-click attribute for GA if it is set and language is Welsh" in {
      val html = LanguageSelection.render(languageMap, langToUrl, None, Some("appName"), messagesWelsh)
      contentType(html)     must be("text/html")
      contentAsString(html) must include("data-journey-click=\"appName:language: en\"")
    }

    "show a data-journey-click attribute for GA if it is set and language is English" in {
      val html = LanguageSelection.render(languageMap, langToUrl, None, Some("appName"), messagesEnglish)
      contentType(html)     must be("text/html")
      contentAsString(html) must include("data-journey-click=\"appName:language: cy\"")
    }

    "show correct current language message when current language is Spanish" in {
      val Spanish = Lang("es")

      val mockLanguageMap = Map("english" -> English, "cymraeg" -> Welsh, "español" -> Spanish)

      val html = LanguageSelection.render(mockLanguageMap, langToUrl, None, None, messagesSpanish)
      contentType(html)     must be("text/html")
      contentAsString(html) must include("<span aria-current=\"true\">Español</span>")
    }
  }
}
