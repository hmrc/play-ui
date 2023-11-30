/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.play.views.formatting

import org.apache.commons.text.WordUtils
import play.api.i18n.Messages

object Strings {

  def sentence(value: String) = value.toLowerCase

  def sentenceStart(value: String) =
    value.substring(0, 1).toUpperCase() + sentence(value).substring(1)

  def capitalise(value: String) = WordUtils.capitalizeFully(value)

  def hyphenate(value: String) = value.split(" ").map(sentence(_)).mkString("-")

  def joinList(values: Traversable[String], separator: String) = values.mkString(separator)

  def optionalValue(value: Option[String], defaultMessageKey: String, isSentence: Boolean = false)(implicit
    messages: Messages
  ) =
    value match {
      case Some(v) => v
      case None    =>
        val message = Messages(defaultMessageKey)
        if (isSentence) sentence(message) else message
    }
}
