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

package uk.gov.hmrc.play.views.config

import com.google.inject.ProvisionException
import org.scalatest.{Matchers, WordSpec}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.config.GTMConfig

class GTMConfigSpec extends WordSpec with Matchers with MessagesSupport {

  def buildApp(properties: Map[String, String]) =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "url and dataLayerUrl" should {
    implicit val request = FakeRequest("GET", "/foo")

    "return the correct urls to the gtm and datalayer snippet" in {
      implicit val application = buildApp(
        Map(
          "gtm.data.layer.url" -> "/foo",
          "gtm.container" -> "a",
          "gtm.a.url" -> "/bar"
        ))
      val config = application.injector.instanceOf[GTMConfig]
      config.url should equal(Some("/bar"))
      config.dataLayerUrl should equal(Some("/foo"))
    }

    "return None if container is not defined" in {
      implicit val application = buildApp(
        Map(
          "gtm.data.layer.url" -> "/foo",
          "gtm.a.url" -> "/bar"
        ))

      val config = application.injector.instanceOf[GTMConfig]
      config.url should equal(None)
      config.dataLayerUrl should equal(None)
    }

    "throw an exception if container is defined but not the url" in {
      implicit val application = buildApp(
        Map(
          "gtm.data.layer.url" -> "/foo",
          "gtm.container" -> "z"
        ))

      assertThrows[ProvisionException] {
        application.injector.instanceOf[GTMConfig]
      }
    }
  }
}
