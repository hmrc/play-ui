/*
 * Copyright 2017 HM Revenue & Customs
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

import org.scalatest.Matchers._
import org.scalatest.WordSpec
import play.api.Configuration

class OptimizelyConfigSpec extends WordSpec {

  "optimizelyConfigUrl" should {

    "return concatenation of values associated with 'optimizely.url' and 'optimizely.projectId'" in new Setup {
      val config = configContaining(
        "optimizely.url" -> "optimizely-url/",
        "optimizely.projectId" -> "project-id"
      )

      config.optimizelyConfigUrl shouldBe Some("optimizely-url/project-id.js")
    }

    "return None if there are no values associated with 'optimizely.url' and 'optimizely.projectId'" in new Setup {
      val config = configContaining()
      config.optimizelyConfigUrl shouldBe None
    }

    "return None if there is no value associated with 'optimizely.url'" in new Setup {
      val config = configContaining("optimizely.projectId" -> "project-id")
      config.optimizelyConfigUrl shouldBe None
    }

    "return None if there is no value associated with'optimizely.projectId'" in new Setup {
      val config = configContaining("optimizely.url" -> "optimizely-url/")
      config.optimizelyConfigUrl shouldBe None
    }
  }

  private trait Setup {
    def configContaining(entries: (String, String)*): OptimizelyConfig = new OptimizelyConfig {
      override protected val configuration: Configuration = Configuration(entries: _*)
    }
  }

}
