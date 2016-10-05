/*
 * Copyright 2016 HM Revenue & Customs
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

import play.api.Play

trait AssetsPrefix {
  lazy val assetsUrl = Play.current.configuration.getString("assets.url").getOrElse("")
  lazy val assetsVersion = Play.current.configuration.getString("assets.version").getOrElse("")
  lazy val assetsPrefix = assetsUrl + assetsVersion
}

object AssetsPrefix extends AssetsPrefix