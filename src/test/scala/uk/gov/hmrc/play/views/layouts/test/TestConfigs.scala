/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.play.views.layouts.test

import play.api.Configuration
import uk.gov.hmrc.play.config.{AssetsConfig, GTMConfig, OptimizelyConfig}

object TestConfigs {

  val testAssetsConfig =
    new AssetsConfig(
      Configuration(
        "assets.url"     -> "doesnt-matter",
        "assets.version" -> "doesnt-matter"
      )
    )

  val testOptimizelyConfig =
    new OptimizelyConfig(
      Configuration(
        "optimizely.url"       -> "doesnt-matter",
        "optimizely.projectId" -> "doesnt-matter"
      )
    )

  val testGTMConfig =
    new GTMConfig(
      Configuration(
        "gtm.container"        -> "transitional",
        "gtm.transitional.url" -> "some transitional url",
        "gtm.main.url"         -> "some main url",
        "gtm.data.layer.url"   -> "some data layer url"
      )
    )

}
