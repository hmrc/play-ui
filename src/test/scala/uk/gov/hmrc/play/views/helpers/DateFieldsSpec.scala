/*
 * Copyright 2024 HM Revenue & Customs
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
import play.api.data.Forms.{mapping, of => fieldOf}
import play.api.data.format.Formats._
import play.api.test.Helpers._
import play.twirl.api.Html
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.views.html.helpers._

class DateFieldsSpec extends WordSpec with Matchers with MessagesSupport {

  val months = Seq(
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
  )

  case class DummyFormData(day: Int, month: Int, year: Int)

  def dummyForm =
    Form(
      mapping(
        "dummyField.day"   -> fieldOf[Int],
        "dummyField.month" -> fieldOf[Int],
        "dummyField.year"  -> fieldOf[Int]
      )(DummyFormData.apply)(DummyFormData.unapply)
    )

  "The Date Fields with a freeform year input box" should {
    "Display months using long nouns" in {
      val dateFieldsFreeYearInline = new DateFieldsFreeYearInline(new Input(), new Dropdown())
      val doc                      = jsoupDocument(dateFieldsFreeYearInline(dummyForm, "dummyField", Html("label")))
      months.zipWithIndex.foreach { case (month: String, i: Int) =>
        doc.getElementById(s"dummyField.month-${i + 1}").text shouldBe month
      }
    }
  }

  "The Date Fields with a limited year input box" should {
    "Display months using long nouns" in {
      val dateFieldsInline = new DateFieldsInline(new Dropdown())
      val doc              = jsoupDocument(dateFieldsInline(dummyForm, "dummyField", Html("label"), 1 to 2, None))
      months.zipWithIndex.foreach { case (month: String, i: Int) =>
        doc.getElementById(s"dummyField.month-${i + 1}").text shouldBe month
      }
    }
  }

}
