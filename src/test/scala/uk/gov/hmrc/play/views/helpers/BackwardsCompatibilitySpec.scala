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

package uk.gov.hmrc.play.views.helpers

import org.scalatest.{Matchers, WordSpec}

/*
 * Verifies that old pre play 2.6 way of accessing templates
 * continues to work after we've made them injectable.
 */
class BackwardsCompatibilitySpec extends WordSpec with Matchers {

  import uk.gov.hmrc.play.views.html.helpers._

  "It should be possible to access templates without DI" in {
    errorInline                shouldBe an[ErrorInline]
    dropdown                   shouldBe a[Dropdown]
    dateFieldsInline           shouldBe a[DateFieldsInline]
    dateFields                 shouldBe a[DateFields]
    dateFieldsFreeInline       shouldBe a[DateFieldsFreeInline]
    fieldGroup                 shouldBe a[FieldGroup]
    address                    shouldBe an[Address]
    dateFieldsFreeInlineLegend shouldBe a[DateFieldsFreeInlineLegend]
    dateFieldsFreeYear         shouldBe a[DateFieldsFreeYear]
    dateFieldsFreeYearInline   shouldBe a[DateFieldsFreeYearInline]
    error_notifications        shouldBe an[ErrorNotifications]
    errorSummary               shouldBe an[ErrorSummary]
    form                       shouldBe a[FormWithCSRF]
    input                      shouldBe an[Input]
    reportAProblemLink         shouldBe a[ReportAProblemLink]
    singleCheckbox             shouldBe a[SingleCheckbox]
    textArea                   shouldBe a[TextArea]
  }

}
