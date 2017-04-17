/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.play.formatters

import org.scalatest.Matchers
import org.scalatest.WordSpec

import play.api.data.FormError
import uk.gov.hmrc.domain.Nino

class NinoFormatterSpecs extends WordSpec with Matchers {

  "Nino formatter" should {

    "unbind correct Nino" in {
      NinoFormatter.nino.unbind("nino", Nino("AA000000A")) shouldBe Map("nino"->"AA000000A")
    }

    "create a mapping for a valid Nino" in {
      val result = NinoFormatter.nino.bind("nino", Map("nino"->"AA000000A"))
      result.isRight shouldBe true
      result.right.get shouldBe Nino("AA000000A")
    }

    "create a mapping for a valid Nino with space symbols" in {
      val result = NinoFormatter.nino.bind("nino", Map("nino"->" AA 00 00 00 A "))
      result.isRight shouldBe true
      result.right.get shouldBe Nino("AA000000A")
    }

    "return a validation error if Nino is not provided" in {
      val result = NinoFormatter.nino.bind("nino", Map())
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("nino", List("error.form.field.required.nino"), List()))
    }
    
    "return a validation error if empty string is provided as Nino" in {
      val result = NinoFormatter.nino.bind("nino", Map("nino"->""))
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("nino", List("error.form.field.required.nino"), List()))
    }
    
    "return a validation error if invalid Nino is provided" in {
      val result = NinoFormatter.nino.bind("nino", Map("nino"->"AAAAAAAAA"))
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("nino", List("error.form.field.invalid.nino"), List()))
    }
  }
}
