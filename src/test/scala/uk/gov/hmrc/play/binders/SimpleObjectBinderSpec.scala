/*
 * Copyright 2017 HM Revenue & Customs
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

class SimpleObjectBinderSpec extends WordSpecLike with Matchers {

  "binding a value" should {
    "return an error for invalid value" in {


      val result : Either[String, TestObject]= TestBinder.bind("key", "short")
      result.isLeft should be(true)
      result.left.get shouldBe "Cannot parse parameter 'key' with value 'short' as 'TestObject'"
    }
  }

}

case class TestObject(value: String) {
  require(value.length > 5)
}

object TestBinder extends SimpleObjectBinder[TestObject](TestObject.apply, _.value)
