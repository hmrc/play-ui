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

package uk.gov.hmrc.play.views.layouts

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

/*
 * Verifies that old pre play 2.6 way of accessing templates
 * continues to work after we've made them injectable.
 */
class BackwardsCompatibilityDIAndStaticSpec extends WordSpec with Matchers with GuiceOneAppPerSuite {
  import uk.gov.hmrc.play.views.html.layouts._

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder().configure(Map("play.allowGlobalApplication" -> "true")).build()

  "It should be possible to access templates without DI" in {
    article             shouldBe an[Article]
    attorney_banner     shouldBe an[AttorneyBanner]
    betaBanner          shouldBe a[BetaBanner]
    footer_links        shouldBe a[FooterLinks]
    header_nav          shouldBe a[HeaderNav]
    loginStatus         shouldBe a[LoginStatus]
    main_content        shouldBe a[MainContent]
    main_content_header shouldBe a[MainContentHeader]
    serviceInfo         shouldBe a[ServiceInfo]
    sidebar             shouldBe a[Sidebar]
  }
}
