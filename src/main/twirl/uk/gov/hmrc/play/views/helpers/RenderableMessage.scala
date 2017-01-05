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

package uk.gov.hmrc.play.views.helpers

import java.text.{DateFormat, SimpleDateFormat}

import org.joda.time.LocalDate
import play.twirl.api.Html
import uk.gov.hmrc.play.views.helpers.RenderableMessageProperty.RenderableMessageProperty
import uk.gov.hmrc.play.views.html.helpers.moneyPoundsRenderer

case class MoneyPounds(value: BigDecimal, decimalPlaces: Int = 2, roundUp: Boolean = false) {

  def isNegative = value < 0

  def quantity = s"%,.${decimalPlaces}f".format(value.setScale(decimalPlaces, if (roundUp) BigDecimal.RoundingMode.CEILING else BigDecimal.RoundingMode.FLOOR).abs)
}

object RenderableMessageProperty extends Enumeration {
  type RenderableMessageProperty = Value

  object Link {
    val ID, TEXT = Value
  }
}

trait RenderableMessage {
  
  def render: Html

  def set(property: (RenderableMessageProperty, String)): RenderableMessage = this
}


object RenderableMessage {
  implicit def translateStrings(value: String): RenderableStringMessage = RenderableStringMessage(value)

  implicit def translateMoneyPounds(money: MoneyPounds): RenderableMoneyMessage = RenderableMoneyMessage(money)

  implicit def translateDate(date: LocalDate): RenderableDateMessage = RenderableDateMessage(date)

  implicit def translateInt(value: Int): RenderableStringMessage = RenderableStringMessage(value.toString)
}

case class RenderableStringMessage(value: String) extends RenderableMessage {
  override def render: Html = Html(value)
}


case class RenderableDateMessage(date: LocalDate)(implicit dateFormat: DateFormat = new SimpleDateFormat("d MMM yyy")) extends RenderableMessage {
  val formattedDate = dateFormat.format(date.toDate)

  override def render: Html = Html(formattedDate)
}

case class RenderableMoneyMessage(moneyPounds: MoneyPounds) extends RenderableMessage {
  override def render: Html = {
    moneyPoundsRenderer(moneyPounds)
  }
}
