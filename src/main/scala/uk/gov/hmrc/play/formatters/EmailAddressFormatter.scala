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
import scala.util.matching.Regex

import play.api.data.FormError
import play.api.data.format.Formatter
import uk.gov.hmrc.emailaddress.EmailAddress

object EmailAddressFormatter {
  def emailAddress(emailRestriction: Option[Regex] = None, maxLength: Option[Int] = None) = new Formatter[EmailAddress] {
    def unbind(key: String, value: EmailAddress) = Map(key -> value.value)
    def bind(key: String, data: Map[String, String]): Either[Seq[FormError], EmailAddress] =
      data.get(key) match {
        case None => Left(List(FormError(key, s"error.form.field.required.${key}")))
        case Some(userEnteredEmail) => userEnteredEmail.trim match {
          case emptyEmail if (emptyEmail.isEmpty()) =>
            Left(List(FormError(key, s"error.form.field.required.${key}")))
          case longEmail if (maxLength.isDefined && longEmail.length() > maxLength.get) =>
            Left(List(FormError(key, s"error.form.field.max-length-violation.${key}")))
          case email if (EmailAddress.isValid(email) && (emailRestriction.isEmpty || email.matches(emailRestriction.get.regex))) =>
            Right(EmailAddress(email))
          case _ =>
            Left(List(FormError(key, s"error.form.field.invalid.${key}")))
        }
      }
  }
}
