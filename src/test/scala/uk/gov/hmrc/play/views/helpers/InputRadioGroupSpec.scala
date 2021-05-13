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

package uk.gov.hmrc.play.views
package helpers

import org.scalatest.{Matchers, WordSpec}
import play.api.data.Forms.{mapping, _}
import play.api.data.{Field, Form, FormError}
import play.api.test.Helpers._
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.views.html.helpers.InputRadioGroup

class InputRadioGroupSpec extends WordSpec with Matchers with MessagesSupport {

  case class DummyFormData(radioValue: String)

  val max = 10

  def dummyForm =
    Form(
      mapping(
        "radioValue" -> text(maxLength = max)
      )(DummyFormData.apply)(DummyFormData.unapply)
    )

  val inputRadioGroup = new InputRadioGroup()

  "@helpers.inputRadioGroup" should {
    "render an option" in {
      val doc   = jsoupDocument(
        inputRadioGroup(dummyForm("radioValue"), Seq("myValue" -> "myLabel"), '_inputClass -> "myInputClass")
      )
      val input = doc.getElementById("radioValue-myvalue")

      input.attr("type")    shouldBe "radio"
      input.attr("name")    shouldBe "radioValue"
      input.attr("value")   shouldBe "myValue"
      input.attr("class")   shouldBe "myInputClass"
      input.parent().text() shouldBe "myLabel"
    }

    "render label for radio button with the correct class" in {
      val doc = jsoupDocument(
        inputRadioGroup(dummyForm("radioValue"), Seq("myValue" -> "myLabel"), '_labelClass -> "labelClass")
      )
      doc.getElementsByAttributeValue("for", "radioValue-myvalue").attr("class") shouldBe "labelClass"
    }

    "render multiple options" in {
      val doc =
        jsoupDocument(inputRadioGroup(dummyForm("radioValue"), Seq("myValue1" -> "myLabel1", "myValue2" -> "myLabel2")))
      doc.getElementById("radioValue-myvalue1") should not be null
      doc.getElementById("radioValue-myvalue2") should not be null
    }

    "render a selected option" in {
      val doc   = jsoupDocument(
        inputRadioGroup(dummyForm.fill(DummyFormData("myValue"))("radioValue"), Seq("myValue" -> "myLabel"))
      )
      val input = doc.getElementById("radioValue-myvalue")
      input.attr("checked") shouldBe "checked"
    }

    "render the radio group label" in {
      val doc = jsoupDocument(
        inputRadioGroup(
          dummyForm("radioValue"),
          Seq("myValue" -> "myLabel"),
          '_legend        -> "My Radio Group",
          '_legendID      -> "radioGroup legendID",
          '_groupDivClass -> "radioGroupDiv",
          '_groupClass    -> "radioGroupFieldset",
          '_labelClass    -> "myLabelClass",
          '_inputClass    -> "inputClass"
        )
      )

      val radioGroupDiv = doc.getElementsByClass("radioGroupDiv").first()
      radioGroupDiv.attr("class") shouldBe "radioGroupDiv"
      val radioGroupFieldset = radioGroupDiv.getElementsByTag("fieldset").first()
      radioGroupFieldset.attr("class")                             shouldBe "radioGroupFieldset"
      radioGroupFieldset.getElementsByTag("legend").first().text() shouldBe "My Radio Group"
      radioGroupFieldset.getElementsByTag("legend").attr("id")     shouldBe "radioGroup legendID"
      radioGroupFieldset.attr("class")                               should not include "form-field--error"
      val radioGroupField = radioGroupFieldset.getElementsByTag("label").first()
      radioGroupField.attr("class") should include("myLabelClass")
      radioGroupField.ownText()   shouldBe "myLabel"
      val radioGroupFieldInput = radioGroupFieldset.getElementsByTag("input")
      radioGroupFieldInput.attr("class") shouldBe "inputClass"
    }

    "renders errors" in {
      val field: Field = Field(
        form = dummyForm,
        name = "",
        constraints = Seq.empty,
        format = None,
        errors = Seq(FormError("error.maxLength", "too long")),
        value = None
      )
      val doc          = jsoupDocument(inputRadioGroup(field, Seq("myValue" -> "myLabel"), '_inputClass -> "myInputClass"))
      doc.getElementsByTag("div").first().attr("class")             should include("form-field--error")
      doc.getElementsByClass("error-notification").first().text() shouldBe "too long"
    }
  }

}
