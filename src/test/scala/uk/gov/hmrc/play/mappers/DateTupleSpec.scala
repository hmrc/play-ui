/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.play.mappers

import org.joda.time.{DateTime, LocalDate}
import org.scalatest.{Matchers, WordSpec}
import play.api.data.FormError
import uk.gov.hmrc.play.mappers.DateFields._

class DateTupleSpec extends WordSpec with Matchers {


  "validDateTuple" should {

    import uk.gov.hmrc.play.mappers.DateTuple._

    trait Setup {

      def input: Map[String, String]

      lazy val result = validDateTuple.bind(input)
    }



    "return error.enter_a_date when day field is missing" in new Setup {

      lazy val input = Map(month -> "2", year -> "2015")
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.enter_a_date"))
    }

    "return error.enter_a_date when month field is missing" in new Setup {

      lazy val input = Map(day -> "1", year -> "2015")
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.enter_a_date"))
    }

    "return error.enter_a_date when year field is missing" in new Setup {

      lazy val input = Map(day -> "1", month -> "2")
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.enter_a_date"))
    }





    "return error.enter_numbers when day field is non-digit" in new Setup {

      lazy val input = Map(day -> "@", month -> "2", year -> "2015")
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.enter_numbers"))
    }

    "return error.enter_numbers when month field is non-digit" in new Setup {

      lazy val input = Map(day -> "1", month -> "j", year -> "2015")
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.enter_numbers"))
    }

    "return error.enter_numbers when year field is non-digit" in new Setup {

      lazy val input = Map(day -> "1", month -> "2", year -> "%")
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.enter_numbers"))
    }





    "return error.enter_valid_date when date is not real" in new Setup {

      lazy val input = Map(day -> "30", month -> "2", year -> "2015")
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.enter_valid_date"))
    }






    "return a date on valid input" in new Setup {

      lazy val input = Map(day -> "28", month -> "2", year -> "2015")
      result.isRight shouldBe true
      result.right.get shouldBe DateTime.parse("2015-02-28")
    }

  }

  "dateTuple" should {

    import uk.gov.hmrc.play.mappers.DateTuple._

    def assertError(dateFields: Map[String, String]) {
      val result = dateTuple.bind(dateFields)
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("", "error.invalid.date.format"))
    }

    "create a mapping for a valid date" in {
      val dateFields = Map(day -> "1", month -> "2", year -> "2014")
      val result = dateTuple.bind(dateFields)
      result.isRight shouldBe true
      result.right.get shouldBe Some(new LocalDate(2014, 2, 1))
    }


    "create a mapping for an invalid date (with space after month, day and year)" in {
      val dateFields = Map(day -> "1 ", month -> "2 ", year -> "2014 ")
      val result = dateTuple.bind(dateFields)
      result.isRight shouldBe true
      result.right.get shouldBe Some(new LocalDate(2014, 2, 1))
    }

    "return None when all the fields are empty" in {
      val dateFields = Map(day -> "", month -> "", year -> "")
      val result = dateTuple.bind(dateFields)
      result.isRight shouldBe true
      result.right.get shouldBe None
    }

    "return a validation error for invalid date with characters" in {
      assertError(Map(day -> "1", month -> "2", year -> "bla"))
    }

    "return a validation error for invalid date with invalid month" in {
      assertError(Map(day -> "1", month -> "23", year -> "2014"))
    }

    "return a validation error for invalid date with only 2 digit year" in {
      assertError(Map(day -> "1", month -> "2", year -> "14"))
    }

    "return a validation error for invalid date with more than 4 digit year" in {
      assertError(Map(day -> "1", month -> "01", year -> "14444"))
    }

    "return a validation error for invalid date with more than 2 digit day" in {
      assertError(Map(day -> "122", month -> "01", year -> "2014"))
    }

    "return a validation error for invalid date with more than 2 digit month" in {
      assertError(Map(day -> "1", month -> "133", year -> "2014"))
    }


  }
}
