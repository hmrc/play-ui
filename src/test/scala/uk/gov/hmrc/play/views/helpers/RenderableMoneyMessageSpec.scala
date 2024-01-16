/*
 * Copyright 2024 HM Revenue & Customs
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

class RenderableMoneyMessageSpec extends WordSpec with Matchers {

  "Money" should {
    "include pound (£) sign before a number" in {
      RenderableMoneyMessage(MoneyPounds(10.50)).render.toString() should include("&pound;10.50")
    }
    "be prepended by a minus if number is negative" in {
      RenderableMoneyMessage(MoneyPounds(-10.50)).render.toString() should include("&minus;&pound;10.50")
    }
  }

}
