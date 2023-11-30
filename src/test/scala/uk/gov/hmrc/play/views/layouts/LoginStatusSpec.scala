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

import org.joda.time.{DateTime, DateTimeZone}
import org.scalatest.{Matchers, WordSpec}
import play.api.i18n._
import play.api.test.Helpers.{contentAsString, _}
import uk.gov.hmrc.play.MessagesSupport
import uk.gov.hmrc.play.views.html.layouts.LoginStatus

class LoginStatusSpec extends WordSpec with Matchers with MessagesSupport {

  "The loginStatus" should {
    val loginStatus          = new LoginStatus()
    val userName             = "Ivor"
    val previouslyLoggedInAt = new DateTime(2018, 4, 20, 16, 20, 0, 0, DateTimeZone.forID("Europe/London"))

    "show the first login message in English" in {
      val content = contentAsString(loginStatus(userName, None, "logoutUrl"))
      content should include("Ivor, this is the first time you have logged in")
    }

    "show the first login message in Welsh" in {
      val welshMessages = messagesApi.preferred(Seq(Lang("cy")))
      val content       = contentAsString(loginStatus(userName, None, "logoutUrl")(welshMessages))
      content should include("Ivor, dyma’r tro cyntaf i chi fewngofnodi")
    }

    "show the previous login message in English" in {
      val content = contentAsString(loginStatus(userName, Some(previouslyLoggedInAt), "logoutUrl"))
      content should include("Ivor, you last signed in 4:20pm, Friday 20 April 2018")
    }

    "show the previous login message in Welsh (with the day and month in Welsh)" in {
      val welshMessages = messagesApi.preferred(Seq(Lang("cy")))
      val content       = contentAsString(loginStatus(userName, Some(previouslyLoggedInAt), "logoutUrl")(welshMessages))
      content should include("Ivor, y tro diwethaf i chi fewngofnodi oedd 4:20pm, Dydd Gwener 20 Ebrill 2018")
    }

  }

}
