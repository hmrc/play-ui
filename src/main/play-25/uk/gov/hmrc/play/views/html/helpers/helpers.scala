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
   * Values added for backwards compatibility as DI is not possible in Play 2.5
   */

  lazy val address                    = new Address(input)
  lazy val dateFields                 = new DateFields(dateFieldsInline)
  lazy val dateFieldsFreeInline       = new DateFieldsFreeInline(input)
  lazy val dateFieldsFreeInlineLegend = new DateFieldsFreeInlineLegend(input)
  lazy val dateFieldsFreeYearInline   = new DateFieldsFreeYearInline(input, dropdown)
  lazy val dateFieldsFreeYear         = new DateFieldsFreeYear(dateFieldsFreeYearInline)
  lazy val dateFieldsInline           = new DateFieldsInline(dropdown)
  lazy val dropdown                   = new Dropdown()
  lazy val errorInline                = new ErrorInline()
  lazy val error_notifications        = new ErrorNotifications()
  lazy val errorSummary               = new ErrorSummary()
  lazy val fieldGroup                 = new FieldGroup(dateFieldsInline, dropdown)
  lazy val form                       = new FormWithCSRF()
  lazy val input                      = new Input()
  lazy val input_radio_group          = new InputRadioGroup()
  lazy val reportAProblemLink         = new ReportAProblemLink()
  lazy val singleCheckbox             = new SingleCheckbox()
  lazy val textArea                   = new TextArea()

}
