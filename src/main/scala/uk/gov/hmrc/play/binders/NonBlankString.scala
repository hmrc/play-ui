/*
 * Copyright 2016 HM Revenue & Customs
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

import play.api.libs.json._
import play.api.mvc.QueryStringBindable

case class NonBlankString(value: String) {
  require(value != null && !value.isEmpty)
}

object NonBlankString {
  implicit val reads = new Reads[NonBlankString] {
    def reads(js: JsValue): JsResult[NonBlankString] = js.validate[String].flatMap {
      case "" | null => JsError("string was blank")
      case s => JsSuccess(NonBlankString(s))
    }
  }

  implicit val writes = new Writes[NonBlankString] {
    def writes(nbs: NonBlankString) = JsString(nbs.value)
  }

  implicit def stringToNonBlankString(implicit stringBinder: QueryStringBindable[String]) = new QueryStringBindable[NonBlankString] {
    def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, NonBlankString]] = {
      stringBinder.bind(key, params).map {
        case Right(null | "") => Left("String was blank")
        case Right(nonBlankValue) => Right(NonBlankString(nonBlankValue))
        case Left(error) => Left(error)
      }
    }

    def unbind(key: String, nbs: NonBlankString): String = nbs.value
  }
}
