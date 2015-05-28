/*
 * Copyright 2015 HM Revenue & Customs
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
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.views.html.helpers.inputRadioGroup

class InputRadioGroupSpec extends WordSpec with Matchers {

  case class DummyFormData(radioValue: String)

  def dummyForm = Form(
    mapping(
      "radioValue" -> text(maxLength = 10)

    )(DummyFormData.apply)(DummyFormData.unapply))

  "@helpers.inputRadioGroup" should {
    "render an option" in {
      val doc = Jsoup.parse(contentAsString(inputRadioGroup(dummyForm("radioValue"), Seq("myValue" -> "myLabel"),'_inputClass -> "myInputClass")))
      val input = doc.getElementById("radioValue-myvalue")

      input.attr("type") shouldBe "radio"
      input.attr("name") shouldBe "radioValue"
      input.attr("value") shouldBe "myValue"
      input.attr("class") shouldBe "myInputClass"
      input.parent().text() shouldBe "myLabel"
    }

    "render label for radio button with the correct class" in {
      val doc = Jsoup.parse(contentAsString(inputRadioGroup(dummyForm("radioValue"), Seq("myValue" -> "myLabel"),'_labelClass -> "labelClass")))
      doc.getElementsByAttributeValue("for","radioValue-myvalue").attr("class") shouldBe "labelClass"
    }

    "render multiple options" in {
      val doc = Jsoup.parse(contentAsString(inputRadioGroup(dummyForm("radioValue"), Seq("myValue1" -> "myLabel1","myValue2" -> "myLabel2"))))
      doc.getElementById("radioValue-myvalue1") should not be null
      doc.getElementById("radioValue-myvalue2") should not be null
    }

    "render a selected option" in {
      val doc = Jsoup.parse(contentAsString(inputRadioGroup(dummyForm.fill(DummyFormData("myValue"))("radioValue"), Seq("myValue" -> "myLabel"))))
      val input = doc.getElementById("radioValue-myvalue")
      input.attr("checked") shouldBe "checked"
    }

    "render the radio group label"  in {
      val doc = Jsoup.parse(contentAsString(inputRadioGroup(dummyForm("radioValue"), Seq("myValue" -> "myLabel"),
        '_legend -> "My Radio Group",
        '_groupClass -> "radioGroup",
        '_labelClass -> "myLabelClass",
        '_inputClass -> "inputClass"
      )))

      val radioGroupFieldset = doc.getElementsByClass("radioGroup").first()
      radioGroupFieldset.getElementsByTag("legend").first().text() shouldBe "My Radio Group"
      radioGroupFieldset.attr("class") should not include "form-field--error"
      val radioGroupField = radioGroupFieldset.getElementsByTag("label").first()
      radioGroupField.attr("class") should include("myLabelClass")
      radioGroupField.ownText() shouldBe "myLabel"
      val radioGroupFieldInput = radioGroupField.getElementsByTag("input")
      radioGroupFieldInput.attr("class") shouldBe "inputClass"
    }

    "renders errors" in {
      val field = dummyForm.bindFromRequest()(FakeRequest().withFormUrlEncodedBody("radioValue" -> "Value is too long!")).fold(
        error => {
          error("radioValue")
        },
        data => throw new Exception
      )
      val doc = Jsoup.parse(contentAsString(inputRadioGroup(field, Seq("myValue" -> "myLabel"),'_inputClass -> "myInputClass")))
      doc.getElementsByTag("fieldset").first().attr("class") should include("form-field--error")
      doc.getElementsByClass("error-notification").first().text() shouldBe "error.maxLength"
    }
  }

}
