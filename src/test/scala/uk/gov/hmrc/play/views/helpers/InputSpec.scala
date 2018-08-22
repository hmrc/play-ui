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
import play.api.data.{Field, Form, FormError}
import play.api.data.Forms.{mapping, text}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Helpers.contentAsString
import play.api.i18n.Messages.Implicits._
import play.api.test.Helpers._
import uk.gov.hmrc.play.views.html.helpers.input
import scala.collection.JavaConverters._


class InputSpec extends WordSpec with Matchers {

  implicit val application = new GuiceApplicationBuilder().build()

  case class DummyFormData(inputBoxValue: String)

  def dummyForm = Form(
    mapping(
      "inputBoxValue" -> text

    )(DummyFormData.apply)(DummyFormData.unapply))

  "@helpers.input" should {
    "render an input box" in {
      val doc = Jsoup.parse(contentAsString(input(field = dummyForm("inputValue"),'_inputClass -> "myInputClass", '_label -> "myLabel")))
      val inputBox = doc.getElementById("inputValue")

      inputBox.attr("type") shouldBe "text"
      inputBox.attr("name") shouldBe "inputValue"
      inputBox.attr("value") shouldBe ""
      inputBox.attr("class") shouldBe "myInputClass"
      inputBox.parent().text() shouldBe "myLabel"
    }

    "render error notification when errors are present" in {
      val doc = Jsoup.parse(contentAsString(input(
        field = Field(dummyForm, "", Seq.empty, None, Seq(FormError("inputBoxValue", "Enter a value")), Some("")),
        '_inputClass -> "myInputClass", '_label -> "myLabel", '_error_id -> "myError")))
      val errorMessage = doc.getElementById("myError").text
      errorMessage shouldBe "Enter a value"
    }

    "render input box with errors in the right order" in {
      val doc = Jsoup.parse(contentAsString(input(
        field = Field(dummyForm, "", Seq.empty, None, Seq(FormError("inputBoxValue", "Form Error Text")), Some("")),
        '_inputClass -> "inputClass",
        '_label -> "Label Text",
        '_error_id -> "errorId",
        '_inputHint -> "Input Hint Text",
        '_labelClass -> "myLabelClass")
      ))
      val inputBox = doc.getElementsByClass("myLabelClass").first
      val listOfInputBoxTextInOrder = inputBox.getElementsByTag("span").asScala.toList.map(_.text)

      listOfInputBoxTextInOrder shouldBe List("Label Text", "Input Hint Text", "Form Error Text")
    }
  }

}
