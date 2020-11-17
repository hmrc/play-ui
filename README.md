<!--
Copyright 2018 HM Revenue & Custom

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

[![Join the chat at https://gitter.im/hmrc/play-ui](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/hmrc/play-ui?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Build Status](https://travis-ci.org/hmrc/play-ui.svg)](https://travis-ci.org/hmrc/play-ui) [ ![Download](https://api.bintray.com/packages/hmrc/releases/play-ui/images/download.svg) ](https://bintray.com/hmrc/releases/play-ui/_latestVersion)

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

### Accessibility Statements

The [FooterLinks](src/main/twirl/uk/gov/hmrc/play/views/layouts/FooterLinks.scala.html) component generates the standard list of links for passing into Gov.UK template.

To configure this component to link to the new 
[Accessibility Statement service](https://www.github.com/hmrc/accessibility-statement-frontend), provide the key 
`accessibility-statement.service-path` in your `application.conf`. This key is the path to your 
accessibility statement under https://www.tax.service.gov.uk/accessibility-statement.
 
For example, if your accessibility statement is https://www.tax.service.gov.uk/accessibility-statement/discounted-icecreams, 
this property must be set to `/discounted-icecreams` as follows:

```
accessibility-statement.service-path = "/discounted-icecreams"
```

### Integrating with Tracking Consent

If you intend to use Google Analytics to measure usage of your service, you will need to integrate with tracking
consent. The [HeadWithTrackingConsent](src/main/twirl/uk/gov/hmrc/play/views/layouts/HeadWithTrackingConsent.scala.html)
component is a replacement to the existing Head component and generates the HTML SCRIPT tags necessary to integrate with
 [tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend)

Before integrating, it is important to remove any existing snippets relating to GTM or Optimizely, for example,
through use of the Head, GTMSnippet or OptimizelySnippet components. Tracking consent
manages the enabling of these third-party solutions based on the user's tracking preferences. If they are not removed
there is a risk the user's tracking preferences will not be honoured.

Configure your service's GTM container in `conf/application.conf`. For example, if you have been
instructed to use GTM container `a`, the configuration would appear as:

```
tracking-consent-frontend {
  gtm.container = "a"
}
```

`gtm.container` can be one of: `transitional`, `a`, `b`, `c`, `d`, `e`, `f` or `sdes`. Consult with the CIPSAGA team 
to understand which GTM container you should be using in your service.

If you are already using the [Head](src/main/twirl/uk/gov/hmrc/play/views/layouts/Head.scala.html) template, simply replace with
[HeadWithTrackingConsent](src/main/twirl/uk/gov/hmrc/play/views/layouts/HeadWithTrackingConsent.scala.html).

If you are not using Head, locate in your frontend code the location where common Javascripts and stylesheets are 
added to the HTML HEAD element. Add TrackingConsentSnippet above the other assets in the HEAD tag. For example,

```
@this(trackingConsentSnippet: TrackingConsentSnippet)

...

@trackingConsentSnippet()

<link href='path-to-asset.css' media="all" rel="stylesheet" type="text/css" />
...
```

If using Play 2.7 and CSPFilter, the nonce can be passed to tracking consent as follows:

```
@import views.html.helper.CSPNonce
...
@trackingConsentSnippet(nonce = CSPNonce.get)
...
```

## Adding to your service

Include the following dependency in your SBT build

```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

libraryDependencies += "uk.gov.hmrc" %% "play-ui" % "x.x.x"
```

If you require either [head](https://github.com/hmrc/play-ui/blob/master/src/main/twirl/uk/gov/hmrc/play/views/layouts/head.scala.html) or [footer](https://github.com/hmrc/play-ui/blob/master/src/main/twirl/uk/gov/hmrc/play/views/layouts/footer.scala.html) you'll also need to add some config to your `application.conf` file in order to build the complete urls for assets:

```
assets {
  version = "x.x.x"
  url = ""
}
```

### How to test changes locally

Publish the library locally with
 
 ```sbt clean compile publishLocal```
 
 This will build and install the library into your local Ivy cache. The final lines of the output will contain the version number. 
 
 Then update the configuration of your frontend application to use this version number. 
 This is in different files depending on the application. 
 In `business-tax-account`, it is in `FrontendBuild.scala`.
 In `one-time-password-frontend`, it is in `MicroServiceBuild.scala`.
 The line will look like 
 ```"uk.gov.hmrc" %% "play-ui" % "4.10"```
 
 now restart your frontend application, and you will see your local changes.
 
 n.b. you will need to run the sbt command to publish locally each time you make a change.


## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
