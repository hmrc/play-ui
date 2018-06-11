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

package uk.gov.hmrc.play.views.html

package object helpers {

  /*
   * Values added for backwards compatibility to not enforce DI.
   */

  @deprecated("Use DI")
  val input = new Input()

  @deprecated("Use DI")
  val dropdown = new Dropdown()

  @deprecated("Use DI")
  val singleCheckbox = new SingleCheckbox()

  @deprecated("Use DI")
  val textArea = new TextArea()

  @deprecated("Use DI")
  val form = new FormWithCSRF()

  @deprecated("Use DI")
  val dateFieldsInline = new DateFieldsInline(dropdown)

  @deprecated("Use DI")
  val dateFields = new DateFields(dateFieldsInline)

  @deprecated("Use DI")
  val dateFieldsFreeInline = new DateFieldsFreeInline(input)

  @deprecated("Use DI")
  val dateFieldsFreeInlineLegend = new DateFieldsFreeInlineLegend(input)

  @deprecated("Use DI")
  val dateFieldsFreeYearInline = new DateFieldsFreeYearInline(input, dropdown)

  @deprecated("Use DI")
  val dateFieldsFreeYear = new DateFieldsFreeYear(dateFieldsFreeYearInline)

  @deprecated("Use DI")
  val fieldGroup = new FieldGroup(dateFieldsInline, dropdown)

  @deprecated("Use DI")
  val errorInline = new ErrorInline()

  @deprecated("Use DI")
  val error_notifications = new ErrorNotifications()

  @deprecated("Use DI")
  val errorSummary = new ErrorSummary()

  @deprecated("Use DI")
  val reportAProblemLink = new ReportAProblemLink()

  @deprecated("Use DI")
  val address = new Address(input)

}
