/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.play.views.html

package object helpers {

  /*
   * Values added for backwards compatibility to not enforce DI.
   */

  @deprecated("Use DI")
  lazy val address = new Address(input)

  @deprecated("Use DI")
  lazy val dateFields = new DateFields(dateFieldsInline)

  @deprecated("Use DI")
  lazy val dateFieldsFreeInline = new DateFieldsFreeInline(input)

  @deprecated("Use DI")
  lazy val dateFieldsFreeInlineLegend = new DateFieldsFreeInlineLegend(input)

  @deprecated("Use DI")
  lazy val dateFieldsFreeYearInline = new DateFieldsFreeYearInline(input, dropdown)

  @deprecated("Use DI")
  lazy val dateFieldsFreeYear = new DateFieldsFreeYear(dateFieldsFreeYearInline)

  @deprecated("Use DI")
  lazy val dateFieldsInline = new DateFieldsInline(dropdown)

  @deprecated("Use DI")
  lazy val dropdown = new Dropdown()

  @deprecated("Use DI")
  lazy val errorInline = new ErrorInline()

  @deprecated("Use DI")
  lazy val error_notifications = new ErrorNotifications()

  @deprecated("Use DI")
  lazy val errorSummary = new ErrorSummary()

  @deprecated("Use DI")
  lazy val fieldGroup = new FieldGroup(dateFieldsInline, dropdown)

  @deprecated("Use DI")
  lazy val form = new FormWithCSRF()

  @deprecated("Use DI")
  lazy val input = new Input()

  @deprecated("Use DI")
  lazy val input_radio_group = new InputRadioGroup()

  @deprecated("Use DI")
  lazy val reportAProblemLink = new ReportAProblemLink()

  @deprecated("Use DI")
  lazy val singleCheckbox = new SingleCheckbox()

  @deprecated("Use DI")
  lazy val textArea = new TextArea()
}
