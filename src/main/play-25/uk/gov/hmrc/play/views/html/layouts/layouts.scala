/*
 * Copyright 2019 HM Revenue & Customs
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
import uk.gov.hmrc.play.config.{AssetsConfig, OptimizelyConfig}

package object layouts {

  /*
   * Values added for backwards compatibility as DI is not possible in Play 2.5
   */

  private lazy val assetsConfig = Play.current.injector.instanceOf[AssetsConfig]

  private lazy val optimizelyConfig = Play.current.injector.instanceOf[OptimizelyConfig]

  lazy val article             = new Article()
  lazy val attorney_banner     = new AttorneyBanner()
  lazy val betaBanner          = new BetaBanner()
  lazy val footer              = new Footer(assetsConfig)
  lazy val eu_exit_links       = new EuExitLinks()
  lazy val footer_links        = new FooterLinks()
  lazy val head                = new Head(optimizely_snippet, assetsConfig)
  lazy val header_nav          = new HeaderNav()
  lazy val loginStatus         = new LoginStatus()
  lazy val main_content        = new MainContent()
  lazy val main_content_header = new MainContentHeader()
  lazy val optimizely_snippet  = new OptimizelySnippet(optimizelyConfig)
  lazy val serviceInfo         = new ServiceInfo()
  lazy val sidebar             = new Sidebar()

}
