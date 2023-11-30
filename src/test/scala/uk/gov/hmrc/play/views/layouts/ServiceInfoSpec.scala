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

package uk.gov.hmrc.play.views.layouts

import org.scalatest.{Matchers, WordSpec}
import play.twirl.api.Html
import uk.gov.hmrc.play.views.html.layouts.ServiceInfo
import play.api.test.Helpers._

class ServiceInfoSpec extends WordSpec with Matchers {

  "The serviceInfo" should {

    val serviceInfo = new ServiceInfo()

    "include hmrc branding when not specified" in {
      val content = contentAsString(serviceInfo(Html("label"), true, None))
      content should include("<div class=\"logo\">")
    }

    "include hmrc branding when specified" in {
      val content = contentAsString(serviceInfo(Html("label"), true, None, true))
      content should include("<div class=\"logo\">")
    }

    "not include hmrc branding when specified" in {
      val content = contentAsString(serviceInfo(Html("label"), true, None, false))
      content should not include "<div class=\"logo\">"
    }

    "appear in english when no parameter is specified" in {
      val content = contentAsString(serviceInfo(Html("label"), true, None, true))
      content should include("HM Revenue &amp; Customs")
      content should not include "Cyllid a Thollau EM"
    }

    "appear in welsh when a parameter of 'cy' is specified" in {
      val content = contentAsString(serviceInfo(Html("label"), true, None, true, "cy"))
      content should include("Cyllid a Thollau EM")
      content should not include "HM Revenue &amp; Customs"
    }
  }
}
