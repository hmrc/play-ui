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

package uk.gov.hmrc.play.binders

import org.scalatest.{EitherValues, Matchers, OptionValues, WordSpecLike}
import uk.gov.hmrc.play.binders.ContinueUrl._

class ContinueUrlSpec extends WordSpecLike with Matchers with EitherValues with OptionValues {

  "isAbsoluteUrl" should {
    "return true for an absolute URL" in {
      ContinueUrl("http://www.example.com").isAbsoluteUrl shouldBe true
    }

    "return false for a relative URL" in {
      ContinueUrl("/service/page").isAbsoluteUrl shouldBe false
    }
  }

  "isRelativeUrl" should {
    "return false for an absolute URL" in {
      ContinueUrl("http://www.example.com").isRelativeUrl shouldBe false
    }

    "return true for a relative URL" in {
      ContinueUrl("/service/page").isRelativeUrl shouldBe true
    }
  }

  "not work for protocol-relative urls" in {
    an[IllegalArgumentException] should be thrownBy ContinueUrl("//some/value?with=query")
    an[IllegalArgumentException] should be thrownBy ContinueUrl("///some/value?with=query")
    an[IllegalArgumentException] should be thrownBy ContinueUrl("////some/value?with=query")
  }

  "not work for urls with @" in {
    an[IllegalArgumentException] should be thrownBy ContinueUrl("/some/value?with=query@meh")
  }

  "not work for path-relative urls" in {
    an[IllegalArgumentException] should be thrownBy ContinueUrl("some/value?with=query")
  }

  "not work for non-urls" in {
    an[IllegalArgumentException] should be thrownBy ContinueUrl("someasdfasdfa")
  }

  "encodedUrl should produce the expected result" in {
    ContinueUrl("/some/value?with=query").encodedUrl shouldBe "%2Fsome%2Fvalue%3Fwith%3Dquery"
  }

  "Binding a continue URL" should {
    "work for host-relative URLs" in {
      val url = "/some/value"
      queryBinder.bind("continue", Map("continue" -> Seq(url))).value.right.value should be(ContinueUrl(url))
    }

    "work for host-relative URLs with query Params" in {
      val url = "/some/value?with=query"
      queryBinder.bind("continue", Map("continue" -> Seq(url))).value.right.value should be(ContinueUrl(url))
    }

    "not work for path-relative urls" in {
      val url = "some/value?with=query"
      queryBinder.bind("continue", Map("continue" -> Seq(url))).value.left.value should be(s"'$url' is not a valid continue URL")
    }

    "not work for non-urls" in {
      val url = "::"
      queryBinder.bind("continue", Map("continue" -> Seq(url))).value.left.value should be(s"'$url' is not a valid continue URL")
    }
  }

  "Unbinding a continue URL" should {
    "return the value" in {
      queryBinder.unbind("continue", ContinueUrl("/some/url")) should be("continue=%2Fsome%2Furl")
    }
  }

}
