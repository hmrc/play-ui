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

package uk.gov.hmrc.play.views.formatting

import org.joda.time.chrono.ISOChronology
import org.joda.time.{DateTime, LocalDate}
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{Matchers, WordSpec}
import play.api.i18n.Lang
import uk.gov.hmrc.play.views.formatting.Dates._

class DatesSpec extends WordSpec with Matchers {
  val UTC = ISOChronology.getInstanceUTC

  "Calling formatDate with a LocalDate object" should {

    "return the formatted date" in {
      val date     = new LocalDate(2010, 9, 22, UTC)
      val expected = dateFormat.print(date)
      formatDate(date) should equal(expected)
    }
  }

  "Calling formatDate with an Optional LocalDate object and a default" should {

    "format the date if the input is Some date" in {
      val date     = Some(new LocalDate(1984, 3, 31, UTC))
      val expected = dateFormat.print(date.get)
      formatDate(date, "the default value") should equal(expected)
    }

    "return the default if the input is None" in {
      val date     = None
      val expected = "the default value"
      formatDate(date, "the default value") should equal(expected)
    }

  }

  "formatDate " should {
    "correctly format given dates " in {
      val dateTable =
        Table(
          ("date", "expectedDateFormat"),
          (new LocalDate(2001, 3, 5, UTC), "5 March 2001"),
          (new LocalDate(1, 1, 1, UTC), "1 January 1"),
          (new LocalDate(999, 1, 1, UTC), "1 January 999"),
          (new LocalDate(2013, 10, 23, UTC), "23 October 2013"),
          (new LocalDate(9999, 12, 31, UTC), "31 December 9999"),
          (new LocalDate(10000, 12, 31, UTC), "31 December 10000")
        )
      forAll(dateTable) { (date: LocalDate, expectedDateFormat: String) =>
        formatDate(date) shouldBe expectedDateFormat
      }
    }
  }

  "formatEasyReadingTimestamp " should {
    "correctly format given dates in english" in {

      implicit val lang: Lang = Lang("en")

      val dateTable =
        Table(
          // UTC internally to -> Lon externally.
          ("date", "expectedDateFormat"),
          (new DateTime(2013, 10, 23, 12, 30, UTC), "1:30pm, Wednesday 23 October 2013"),
          (new DateTime(1899, 7, 3, 12, 30, UTC), "12:30pm, Monday 3 July 1899")
        )
      forAll(dateTable) { (date: DateTime, expectedDateFormat: String) =>
        formatEasyReadingTimestamp(Some(date), "") shouldBe expectedDateFormat
      }
    }

    "correctly format given dates in welsh" in {

      implicit val lang: Lang = Lang("cy")

      val dateTable =
        Table(
          // UTC internally to -> Lon externally.
          ("date", "expectedDateFormat"),
          (new DateTime(2013, 10, 23, 12, 30, UTC), "1:30pm, Dydd Mercher 23 Hydref 2013"),
          (new DateTime(1899, 7, 3, 12, 30, UTC), "12:30pm, Dydd Llun 3 Gorffennaf 1899")
        )
      forAll(dateTable) { (date: DateTime, expectedDateFormat: String) =>
        formatEasyReadingTimestamp(Some(date), "") shouldBe expectedDateFormat
      }
    }
  }

  "shortDate " should {
    "correctly format given dates " in {
      val dateTable =
        Table(
          ("date", "expectedDateFormat"),
          (new LocalDate(1899, 5, 5, UTC), "1899-05-05"),
          (new LocalDate(2013, 10, 23, UTC), "2013-10-23")
        )
      forAll(dateTable) { (date: LocalDate, expectedDateFormat: String) =>
        shortDate(date) shouldBe expectedDateFormat
      }
    }
  }
}
