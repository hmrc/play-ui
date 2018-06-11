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

package uk.gov.hmrc.play.views.html

package object layouts {

  /*
   * Values added for backwards compatibility to not enforce DI.
   */

  @deprecated("Use DI")
  val article = new Article()

  @deprecated("Use DI")
  val attorney_banner = new AttorneyBanner()

  @deprecated("Use DI")
  val betaBanner = new BetaBanner()

  @deprecated("Use DI")
  val footer_links = new FooterLinks()

  @deprecated("Use DI")
  val header_nav = new HeaderNav()

  @deprecated("Use DI")
  val loginStatus = new LoginStatus()

  @deprecated("Use DI")
  val main_content = new MainContent()

  @deprecated("Use DI")
  val main_content_header = new MainContentHeader()

  @deprecated("Use DI")
  val serviceInfo = new ServiceInfo()

  @deprecated("Use DI")
  val sidebar = new Sidebar()
}
