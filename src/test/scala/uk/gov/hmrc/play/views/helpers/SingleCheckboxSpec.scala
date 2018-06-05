/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.play.views.helpers

import org.jsoup.Jsoup
import org.scalatest.{Matchers, WordSpec}
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.test.Helpers._
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.views.html.helpers.singleCheckbox

class SingleCheckboxSpec extends WordSpec with Matchers with MessagesSupport {

  case class DummyFormData(exampleCheckbox: Option[Boolean])

  def dummyForm = Form(
    mapping(
      "exampleCheckbox" -> optional(boolean)
    )(DummyFormData.apply)(DummyFormData.unapply))

  "Have the checked attribute when value is 'true'" in {

    val WithTrueCheckboxValueForm = dummyForm.fill(DummyFormData(Some(true)))
    val doc = Jsoup.parse(
      contentAsString(
        singleCheckbox(
          WithTrueCheckboxValueForm("exampleCheckbox"),
          '_label -> "exampleLabel",
          '_inputClass -> "inputClass",
          '_labelClass -> "labelClass"
        )
      )
    )

    val checkboxElement = doc.getElementById("exampleCheckbox")
    val labelElement = doc.select("label")

    checkboxElement.hasAttr("checked") shouldBe true
    checkboxElement.attr("checked") shouldBe "checked"
    checkboxElement.hasClass("inputClass") shouldBe true
    labelElement.text() shouldBe "exampleLabel"
    labelElement.hasClass("labelClass") shouldBe true
    labelElement.hasClass("selected") shouldBe true
  }

  "The Single CheckBox" should {
    "Not have the checked attribute when value is 'None'" in {

      val doc = Jsoup.parse(
        contentAsString(
          singleCheckbox(
            dummyForm("exampleCheckbox"),
            '_label -> "exampleLabel",
            '_inputClass -> "inputClass",
            '_labelClass -> "labelClass"
          )
        )
      )

      val checkboxElement = doc.getElementById("exampleCheckbox")
      val labelElement = doc.select("label")

      checkboxElement.hasAttr("checked") shouldBe false
      checkboxElement.hasClass("inputClass") shouldBe true
      labelElement.text() shouldBe "exampleLabel"
      labelElement.hasClass("labelClass") shouldBe true
      labelElement.hasClass("selected") shouldBe false
    }

    "Not have the checked attribute when value is 'false'" in {
      val WithFalseCheckboxValueForm = dummyForm.fill(DummyFormData(Some(false)))
      val doc = Jsoup.parse(
        contentAsString(
          singleCheckbox(
            WithFalseCheckboxValueForm("exampleCheckbox"),
            '_label -> "exampleLabel",
            '_inputClass -> "inputClass",
            '_labelClass -> "labelClass"
          )
        )
      )

      val checkboxElement = doc.getElementById("exampleCheckbox")
      val labelElement = doc.select("label")

      checkboxElement.hasAttr("checked") shouldBe false
      checkboxElement.hasClass("inputClass") shouldBe true
      labelElement.text() shouldBe "exampleLabel"
      labelElement.hasClass("labelClass") shouldBe true
      labelElement.hasClass("selected") shouldBe false
    }
  }
}
