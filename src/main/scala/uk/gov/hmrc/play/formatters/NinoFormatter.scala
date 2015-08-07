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

import scala.Left
import scala.Right

import play.api.data.FormError
import play.api.data.format.Formatter
import uk.gov.hmrc.domain.Nino

object NinoFormatter {
  def nino = new Formatter[Nino] {
    override def unbind(key: String, value: Nino) = Map(key -> value.nino)
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Nino] =
      data.get(key) match {
        case None => Left(List(FormError(key, s"error.form.field.required.${key}")))
        case Some(userEnteredNino) => userEnteredNino.replaceAll(" ", "").toUpperCase() match {
          case emptyNino if (emptyNino.isEmpty()) =>
            Left(List(FormError(key, s"error.form.field.required.${key}")))
          case nino if (Nino.isValid(nino)) =>
            Right(Nino(nino))
          case _ =>
            Left(List(FormError(key, s"error.form.field.invalid.${key}")))
        }
      }
  }
} 
