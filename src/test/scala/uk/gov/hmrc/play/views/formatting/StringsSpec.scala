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

package uk.gov.hmrc.play.views.formatting

import org.scalatest.{ Matchers, WordSpec }

class StringsSpec extends WordSpec with Matchers {

  "Strings" should {
    "convert mixed case text into sentence case" in {
      Strings.sentence("Fred Flintstone") should be("fred flintstone")
    }

    "convert mixed case text into sentence start case" in {
      Strings.sentenceStart("fred Flintstone") should be("Fred flintstone")
    }

    "convert lower case into capital case" in {
      Strings.capitalise("fred flintstone") should be("Fred Flintstone")
    }

    "convert mixed case text into lower case hyphenated text" in {
      Strings.hyphenate("Fred flintstone") should be("fred-flintstone")
    }
  }
}
