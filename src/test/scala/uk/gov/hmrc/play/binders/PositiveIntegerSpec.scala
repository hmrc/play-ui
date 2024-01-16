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

package uk.gov.hmrc.play.binders

import org.scalatest.{Matchers, WordSpecLike}

class PositiveIntegerSpec extends WordSpecLike with Matchers {

  "Creating a PositiveInteger" should {
    "throw an exception for a negative number" in {
      an[IllegalArgumentException] should be thrownBy PositiveInteger(-1)
    }

    "throw an exception for zero" in {
      an[IllegalArgumentException] should be thrownBy PositiveInteger(0)
    }

    "give a success for a positive number" in {
      PositiveInteger(1).value shouldBe 1
    }
  }

  "Binding from a query string" should {
    "give an error for a negative number" in {
      binding("-1") shouldBe Some(Left("number was <= 0"))
    }

    "give an error for zero" in {
      binding("0") shouldBe Some(Left("number was <= 0"))
    }

    "give a success for a positive number" in {
      binding("1") shouldBe Some(Right(PositiveInteger(1)))
    }
    def binding(s: String) = PositiveInteger.stringToPositiveInteger.bind("v", Map("v" -> Seq(s)))
  }

  "Unbinding to a query string" should {
    "extract the value" in {
      unbinding(PositiveInteger(5)) shouldBe "5"
    }
    def unbinding(n: PositiveInteger) = PositiveInteger.stringToPositiveInteger.unbind("v", n)
  }

}
