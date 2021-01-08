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

package uk.gov.hmrc.play.binders

import org.scalatest.{Matchers, WordSpecLike}
import play.api.libs.json.{JsError, JsString, JsSuccess, Json}

class NonBlankStringSpec extends WordSpecLike with Matchers {

  "Creating a NonBlankString" should {
    "throw an exception for a blank string" in {
      an[IllegalArgumentException] should be thrownBy NonBlankString("")
    }

    "give an error for a null string" in {
      an[IllegalArgumentException] should be thrownBy NonBlankString(null)
    }

    "give a success for a non-blank string" in {
      NonBlankString("x")
    }
  }

  "Reading a NonBlankString" should {
    "give an error for a blank string" in {
      validating("") shouldBe a[JsError]
    }

    "give an error for a null string" in {
      validating(null) shouldBe a[JsError]
    }

    "give a success for a non-blank string" in {
      validating("x") should be(JsSuccess(NonBlankString("x")))
    }

    def validating(s: String) = JsString(s).validate[NonBlankString]
  }

  "Writing a NonBlankString" should {
    "just include the value" in {
      Json.toJson(NonBlankString("x")) should be(JsString("x"))
    }
  }

  "Binding from a query string" should {
    "give an error for a blank string" in {
      binding("") shouldBe Some(Left("String was blank"))
    }

    "give an error for a null string" in {
      binding(null) shouldBe Some(Left("String was blank"))
    }

    "give a success for a non-blank string" in {
      binding("x") shouldBe Some(Right(NonBlankString("x")))
    }
    def binding(s: String) = NonBlankString.stringToNonBlankString.bind("v", Map("v" -> Seq(s)))
  }

  "Unbinding to a query string" should {
    "extract the value" in {
      unbinding(NonBlankString("something")) shouldBe "something"
    }
    def unbinding(n: NonBlankString) = NonBlankString.stringToNonBlankString.unbind("v", n)
  }

}
