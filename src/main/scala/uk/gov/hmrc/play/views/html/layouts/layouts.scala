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

package uk.gov.hmrc.play.views.html

import play.api.Play
import uk.gov.hmrc.play.config.{AccessibilityStatementConfig, AssetsConfig, GTMConfig, OptimizelyConfig, TrackingConsentConfig}

package object layouts {

  /*
   * Values added for backwards compatibility to not enforce DI.
   */

  private lazy val assetsConfig = Play.current.injector.instanceOf[AssetsConfig]

  private lazy val optimizelyConfig = Play.current.injector.instanceOf[OptimizelyConfig]

  private lazy val gtmConfig = Play.current.injector.instanceOf[GTMConfig]

  private lazy val accessibilityStatementConfig = Play.current.injector.instanceOf[AccessibilityStatementConfig]

  private lazy val trackingConsentConfig = Play.current.injector.instanceOf[TrackingConsentConfig]

  @deprecated("Use DI")
  lazy val article = new Article()

  @deprecated("Use DI")
  lazy val attorney_banner = new AttorneyBanner()

  @deprecated("Use DI")
  lazy val betaBanner = new BetaBanner()

  @deprecated("Use DI")
  lazy val footer = new Footer(assetsConfig)

  @deprecated("Use DI")
  lazy val eu_exit_links = new EuExitLinks()

  @deprecated("Use DI")
  lazy val footer_links = new FooterLinks(accessibilityStatementConfig)

  @deprecated("Use DI")
  lazy val head = new Head(assetsConfig, gtmSnippet)

  @deprecated("Use DI")
  lazy val headWithTrackingConsent = new HeadWithTrackingConsent(trackingConsentSnippet, assetsConfig)

  @deprecated("Use DI")
  lazy val header_nav = new HeaderNav()

  @deprecated("Use DI")
  lazy val loginStatus = new LoginStatus()

  @deprecated("Use DI")
  lazy val main_content = new MainContent()

  @deprecated("Use DI")
  lazy val main_content_header = new MainContentHeader()

  @deprecated("Use DI")
  lazy val gtmSnippet = new GTMSnippet(gtmConfig)

  @deprecated("Use DI")
  lazy val trackingConsentSnippet = new TrackingConsentSnippet(trackingConsentConfig, optimizelyConfig)

  @deprecated("Use DI")
  lazy val serviceInfo = new ServiceInfo()

  @deprecated("Use DI")
  lazy val sidebar = new Sidebar()
}
