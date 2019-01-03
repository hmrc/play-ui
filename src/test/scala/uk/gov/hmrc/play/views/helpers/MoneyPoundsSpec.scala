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

class MoneyPoundsSpec extends WordSpec with Matchers {

  "quantity" should {

    "return the formatted value with 2 decimal places" in {
      MoneyPounds(4.23456, 2).quantity shouldBe "4.23"
      MoneyPounds(76).quantity shouldBe "76.00"
    }

    "return the formatted value with no decimal places" in {
      MoneyPounds(876.93456, 0).quantity shouldBe "876"
      MoneyPounds(987, 0).quantity shouldBe "987"
    }

    "return the formatted value (with grouping separators) and no decimal places" in {
      MoneyPounds(9657876.93456, 0).quantity shouldBe "9,657,876"
      MoneyPounds(1008, 0).quantity shouldBe "1,008"
    }

    "return the formatted value (with grouping separators) and 2 decimal places" in {
      MoneyPounds(9657876.93756, 2).quantity shouldBe "9,657,876.93"
      MoneyPounds(1008, 2).quantity shouldBe "1,008.00"
    }

    "return the formatted value (with grouping separators) and 2 decimal places rounding up" in {
      MoneyPounds(9657876.93756, 2, true).quantity shouldBe "9,657,876.94"
    }

    "return the formatted value (with grouping separators) and no decimal places rounding up" in {
      MoneyPounds(9657876.93456, 0, true).quantity shouldBe "9,657,877"
    }


  }

}
