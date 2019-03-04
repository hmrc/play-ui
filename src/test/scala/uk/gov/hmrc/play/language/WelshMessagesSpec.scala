/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.play.language

import org.scalatest.{Matchers, WordSpecLike}
import play.api.Application
import play.api.Configuration
import play.api.i18n.{Lang, MessagesApi}
import play.api.inject.guice.GuiceApplicationBuilder

class WelshMessagesSpec extends WordSpecLike with Matchers {

  val configuration = Configuration.from(Map("play.i18n.langs" -> List("en", "cy"), "play.i18n.path" -> null))

  val app: Application =
    new GuiceApplicationBuilder()
      .configure(configuration)
      .build()

  val messagesApi: MessagesApi = app.injector.instanceOf(classOf[MessagesApi])

  val allMessages = messagesApi.messages

  val defaultMessageKeys = allMessages("default").keySet
  val welshMessagesKeys = allMessages("cy").keySet

  "all default messages" should {

    "have a welsh translation" in new SetupMessageKeysToIgnore {
      val keysMissingForWelsh = defaultMessageKeys.diff(welshMessagesKeys).filterNot(keyIgnoreRule)

      withClue(s"Following keys are missing for welsh:\n${keysMissingForWelsh.mkString(",\n")}\n") {
        keysMissingForWelsh.size shouldBe 0
      }
    }
  }

  "welsh translations" should {

    val expectedTranslations = Map[String, String](
      "date.fields.day" -> "Diwrnod",
      "date.fields.month" -> "Mis",
      "date.fields.year" -> "Blwyddyn",
      "error.invalid.date.format" -> "Rhaid i chi nodi dyddiad dilys",
      "label.beta" -> "BETA",
      "label.beta.newservice" -> "Mae hwn yn wasanaeth newydd",
      "label.beta.improve" -> "yn ein helpu i'w wella.",
      "label.beta.feedback" -> "adborth",
      "label.beta.yours" -> "– bydd eich",
      "report.a.problem.link" -> "Help gyda'r dudalen hon.",
      "footer.links.cookies.text" -> "Cwcis",
      "footer.links.privacy_policy.text" -> "Polisi preifatrwydd",
      "footer.links.terms_and_conditions.text" -> "Telerau ac Amodau",
      "footer.links.help_page.text" -> "Help wrth ddefnyddio GOV.UK",
      "footer.links.help_page.url" -> "https://www.gov.uk/help",
      "footer.links.cookies.url" -> "/help/cookies",
      "footer.links.privacy_policy.url" -> "/help/privacy",
      "footer.links.terms_and_conditions.url" -> "/help/terms-and-conditions"
    )

    expectedTranslations.foreach{ case (key: String, expectedTranslation: String) =>
      val args = Seq[String]("arg0", "arg1")

      val welshTranslation = messagesApi.apply(key, args:_*)(Lang("cy"))
      val defaultTranslation = messagesApi.apply(key, args:_*)(Lang.defaultLang)

      s"have expected value for key $key" in {
        welshTranslation shouldBe expectedTranslation
      }

      s"equal number of replacements made for $key" in new SetupStringWithOccurrenceCounting {
        args.foreach { arg =>
          welshTranslation.occurrencesOf(arg) shouldBe defaultTranslation.occurrencesOf(arg)
        }
      }
    }
  }

  trait SetupMessageKeysToIgnore {

    // TODO this should be an empty set once all messages have been translated
    val keysPendingTranslation = Set(
      "error.enter_a_date",
      "error.enter_numbers_",
      "error.enter_valid_date",
      "error.email",
      "error.positive.number",
      "error.address.main.line.max.length.violation",
      "error.address.optional.line.max.length.violation",
      "error.address.blank",
      "error.address.invalid.character",
      "error.postcode.length.violation",
      "error.postcode.invalid.character",
      "common.firstlogin",
      "common.previousLoginTime",
      "common.signOut",
      "attorney.banner.link",
      "attorney.banner.user",
      "pertax.attorney.banner.link",
      "pertax.attorney.banner.user",
      "attorney.banner.nan"
    )

    val keyIgnoreRule: String => Boolean = keysPendingTranslation.contains
  }

  trait SetupStringWithOccurrenceCounting {
    implicit class StringWithOccurrenceCounting(val haystack: String) {
      def occurrencesOf(needle: String): Int =
        needle.r.findAllMatchIn(haystack).length
    }
  }
}
