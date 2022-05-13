/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.play.views
package helpers
import org.scalatest.{Matchers, WordSpec}
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.views.html.helpers.FormWithCSRF

class FormWithCSRFSpec extends WordSpec with Matchers with MessagesSupport with CSRFSpec {

  "@helpers.formWithCSRF" should {
    val form       = new FormWithCSRF()
    val simpleCall = Call(method = "POST", url = "/the-post-url")

    "render with the correct action attribute" in {
      val formElement =
        jsoupDocument(form(action = simpleCall)(HtmlFormat.empty))
          .select("form")

      formElement.attr("action") shouldBe "/the-post-url"
    }

    "render with the correct action including a fragment" in {
      val formElement =
        jsoupDocument(form(action = simpleCall.withFragment("tab"))(HtmlFormat.empty))
          .select("form")

      formElement.attr("action") shouldBe "/the-post-url#tab"
    }

    "render with the correct method" in {
      val getCall = Call(method = "GET", url = "/the-post-url")

      val formElement =
        jsoupDocument(form(action = getCall)(HtmlFormat.empty))
          .select("form")

      formElement.attr("method") shouldBe "GET"
    }

    "render the passed attributes" in {
      val formElement =
        jsoupDocument(form(action = simpleCall, 'attribute1 -> "value1")(HtmlFormat.empty))
          .select("form")

      formElement.attr("attribute1") shouldBe "value1"
    }

    "render multiple attributes" in {
      val formElement =
        jsoupDocument(form(action = simpleCall, 'attribute1 -> "value1", 'attribute2 -> "value2")(HtmlFormat.empty))
          .select("form")

      formElement.attr("attribute1") shouldBe "value1"
      formElement.attr("attribute2") shouldBe "value2"
    }

    "render the contents of the form" in {
      val content     = Html("<p>Content</p>")
      val formElement =
        jsoupDocument(form(action = simpleCall)(content))
          .select("p")

      formElement.outerHtml shouldBe "<p>Content</p>"
    }

    "not render the CSRF token if the request does not contain the token" in {
      implicit val request = FakeRequest()
      val formElement      =
        jsoupDocument(form(action = simpleCall)(HtmlFormat.empty))

      val input = formElement.select("input")
      input.size shouldBe 0
    }

    "render the CSRF token" in {
      val formElement =
        jsoupDocument(form(action = simpleCall)(HtmlFormat.empty))

      val input = formElement.select("input")
      input.size shouldBe 1

      input.attr("type")       shouldBe "hidden"
      input.attr("name")       shouldBe "csrfToken"
      input.attr("value").length should be > 0
    }
  }
}
