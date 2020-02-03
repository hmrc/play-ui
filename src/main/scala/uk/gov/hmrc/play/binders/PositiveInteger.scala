/*
 * Copyright 2020 HM Revenue & Customs
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

import play.api.mvc.QueryStringBindable

case class PositiveInteger(value: Int) {
  require(value > 0, s"$value was not a positive integer")
}

object PositiveInteger {
  implicit def toInt(y: PositiveInteger): Int = y.value

  implicit def stringToPositiveInteger(
    implicit stringBinder: QueryStringBindable[Int]): QueryStringBindable[PositiveInteger] =
    new QueryStringBindable[PositiveInteger] {
      def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, PositiveInteger]] =
        stringBinder.bind(key, params).map {
          case Right(x) if x <= 0 => Left("number was <= 0")
          case Right(y)           => Right(PositiveInteger(y))
          case Left(error)        => Left(error)
        }

      def unbind(key: String, natural: PositiveInteger): String = natural.value.toString
    }
}
