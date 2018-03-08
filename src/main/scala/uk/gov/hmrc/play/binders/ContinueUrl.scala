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

import java.net.URLEncoder

import play.api.mvc.QueryStringBindable

import scala.util.{Failure, Success, Try}

case class ContinueUrl(url: String) {

  val isAbsoluteUrl = url.startsWith("http")

  val isRelativeUrl = url.matches("^[/][^/].*")

  require(
    (isRelativeUrl || isAbsoluteUrl) && !url.contains("@"),
    ContinueUrl.errorFor(url)
  )

  def isRelativeOrDev(env: String) = isRelativeUrl || env == "Dev"

  lazy val encodedUrl = URLEncoder.encode(url, "UTF-8")

}

object ContinueUrl {
  private def errorFor(invalidUrl: String) = s"'$invalidUrl' is not a valid continue URL"

  implicit def queryBinder(implicit stringBinder: QueryStringBindable[String]) = new QueryStringBindable[ContinueUrl] {
    def bind(key: String, params: Map[String, Seq[String]]) =
      stringBinder.bind(key, params).map {
        case Right(s) => Try(ContinueUrl(s)) match {
          case Success(url) => Right(url)
          case Failure(_) => Left(errorFor(s))
        }
        case Left(message) => Left(message)
      }

    def unbind(key: String, value: ContinueUrl) = stringBinder.unbind(key, value.url)

  }
}
