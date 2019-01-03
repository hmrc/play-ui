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

package uk.gov.hmrc.play.views
package helpers

import org.scalatest.{Matchers, WordSpec}
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.test.Helpers._
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.views.html.helpers.Dropdown

class DropDownSpec extends WordSpec with Matchers with MessagesSupport {

  case class DummyFormData(country: String)

  def dummyForm = Form(
    mapping(
      "country" -> text
    )(DummyFormData.apply)(DummyFormData.unapply))

  val dropdown = new Dropdown()

  "@helpers.dropDown" should {
    "render element options" in {

      val countries = Seq("AU" -> "Australia", "JP" -> "Japan")

      val doc = jsoupDocument(dropdown(dummyForm("country"), countries, displayEmptyValue = false))

      doc.getElementById("country-AU").outerHtml() shouldBe "<option id=\"country-AU\" value=\"AU\">Australia</option>"
      doc.getElementById("country-JP").outerHtml() shouldBe "<option id=\"country-JP\" value=\"JP\">Japan</option>"
    }
  }

}
