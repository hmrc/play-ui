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

package uk.gov.hmrc.play.validators

import org.scalatest.{Matchers, WordSpec}

class ValidatorsSpec extends WordSpec with Matchers {

  //Valid Characters Alphanumeric (A-Z, a-z, 0-9), hyphen( - ), apostrophe ( ' ), comma ( , ), forward slash ( / ) ampersand ( & ) and space
  // (48 to 57 0-9) (65 to 90 A-Z) (97 to 122 a-z) (32 space) (38 ampersand ( & )) (39 apostrophe ( ' )) (44 comma ( , ))   (45 hyphen( - )) (47 forward slash ( / ))

  " Valid character checker " should {
    " return false if an invalid character is present in an input " in {
      val digits = for (i <- 48 to 57) yield i
      val lowerCaseLetters = for (i <- 97 to 122) yield i
      val upperCaseLetters = for (i <- 65 to 90) yield i
      val specialCharacters = List(32, 38, 39, 44, 45, 47)

      val validCharacters = digits ++ lowerCaseLetters ++ upperCaseLetters ++ specialCharacters

      for (chr <- 0 to 1000) {
        val c   = chr.toChar
        val str = s"this $chr contains $c"
        characterValidator.containsValidAddressCharacters(str) match {
          case true  => validCharacters.contains(chr) should be(true)
          case false => validCharacters.contains(chr) should be(false)
        }
      }
    }
    " return true when None is passed as the value" in {
      characterValidator.containsValidAddressCharacters("") should be(true)
    }
  }

}
