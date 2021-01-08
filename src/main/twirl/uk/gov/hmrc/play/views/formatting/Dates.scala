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

package uk.gov.hmrc.play.views.formatting

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone, LocalDate}
import play.api.i18n.Lang

object Dates {

  private val MonthNamesInWelsh = Map(
    1  -> "Ionawr",
    2  -> "Chwefror",
    3  -> "Mawrth",
    4  -> "Ebrill",
    5  -> "Mai",
    6  -> "Mehefin",
    7  -> "Gorffennaf",
    8  -> "Awst",
    9  -> "Medi",
    10 -> "Hydref",
    11 -> "Tachwedd",
    12 -> "Rhagfyr")
  private val WeekDaysInWelsh = Map(
    1 -> "Dydd Llun",
    2 -> "Dydd Mawrth",
    3 -> "Dydd Mercher",
    4 -> "Dydd Iau",
    5 -> "Dydd Gwener",
    6 -> "Dydd Sadwrn",
    7 -> "Dydd Sul")

  private[formatting] val dateFormat =
    DateTimeFormat.forPattern("d MMMM y").withZone(DateTimeZone.forID("Europe/London"))
  private[formatting] val dateFormatAbbrMonth =
    DateTimeFormat.forPattern("d MMM y").withZone(DateTimeZone.forID("Europe/London"))
  private[formatting] val shortDateFormat =
    DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.forID("Europe/London"))
  private[formatting] val easyReadingDateFormat =
    DateTimeFormat.forPattern("EEEE d MMMM yyyy").withZone(DateTimeZone.forID("Europe/London"))
  private[formatting] val easyReadingTimestampFormat =
    DateTimeFormat.forPattern("h:mmaa").withZone(DateTimeZone.forID("Europe/London"))

  def formatDate(date: LocalDate) = dateFormat.print(date)

  def formatDateAbbrMonth(date: LocalDate) = dateFormatAbbrMonth.print(date)

  def formatDate(date: Option[LocalDate], default: String) = date match {
    case Some(d) => dateFormat.print(d)
    case None    => default
  }

  def formatDateTime(date: DateTime) = dateFormat.print(date)

  def formatEasyReadingTimestamp(date: Option[DateTime], default: String)(implicit lang: Lang) = {
    val englishEasyDate: DateTime => String = d =>
      s"${easyReadingTimestampFormat.print(d).toLowerCase}, ${easyReadingDateFormat.print(d)}"
    val welshEasyDate: DateTime => String = d =>
      s"${easyReadingTimestampFormat.print(d).toLowerCase}, ${WeekDaysInWelsh(d.getDayOfWeek)} ${d.getDayOfMonth} ${MonthNamesInWelsh(
        d.getMonthOfYear)} ${d.getYear}"
    val formatter = lang.code match {
      case "cy" => welshEasyDate
      case _    => englishEasyDate
    }

    date.fold(default)(formatter)
  }

  def shortDate(date: LocalDate) = shortDateFormat.print(date)

  def formatDays(days: Int) = s"$days day${if (days > 1) "s" else ""}"

}
