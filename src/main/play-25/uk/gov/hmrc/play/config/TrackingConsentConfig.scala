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

package uk.gov.hmrc.play.config

import javax.inject.Inject
import play.api.Configuration

class TrackingConsentConfig @Inject()(configuration: Configuration) {
  val trackingConsentBaseUrl: Option[String] = configuration.getString(s"tracking-consent-frontend.url")
  val optimizelyBundle: Option[String] =
    configuration.getString("tracking-consent-frontend.snippets.optimizely")
  val gtmContainer: Option[String] = configuration.getString("gtm.container")

  def optimizelyUrl(): Option[String] =
    for {
      baseUrl <- trackingConsentBaseUrl
      bundle  <- optimizelyBundle
    } yield {
      s"$baseUrl$bundle"
    }

  def url: Option[String] =
    for {
      baseUrl   <- trackingConsentBaseUrl
      container <- gtmContainer
      bundle    <- trackingConsentSnippet(container)
    } yield {
      s"$baseUrl$bundle"
    }

  private def trackingConsentSnippet(container: String): Option[String] =
    configuration.getString(s"tracking-consent-frontend.snippets.$container")
}
