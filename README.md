<!--
Copyright 2015 HM Revenue & Customs

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
play-ui
=======

[![Build Status](https://travis-ci.org/hmrc/play-ui.svg)](https://travis-ci.org/hmrc/play-ui) [![Join the chat at https://gitter.im/hmrc/play-ui](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/hmrc/play-ui?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Micro-library containing core for HMRC's Play UI:
 
### Formatters
* `Dates` provides various human-readable date formats
* `Money` formats pounds and pence
* `Strings` changes the case of phrases and hyphenates

### Form Mappers & Validators
* `DateTuple` maps year, month, day fields to a `LocalDate`
* `StopOnFirstFail` applies constraints in order and fails fast
* `Validators` contains multiple small validation functions such as `addressTuple`, `positiveInteger` & `nonEmptySmallText`

### Helper Templates
Has many standard snippets for form fields, such as: `address`, `dateFields`, `dropdown`, `fieldGroup`. Each helper 
correctly adds labels, error messages and CSS classes.

### Layout Templates
Contains templates for components used across frontend applications such as: header, footer, sidebar, betaBanner.

## Adding to your service

Include the following dependency in your SBT build

```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

libraryDependencies += "uk.gov.hmrc" %% "play-ui" % "1.8.0"
```
