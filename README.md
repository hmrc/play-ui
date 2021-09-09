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
through use of the Head or GTMSnippet components. Tracking consent
manages the enabling of these third-party solutions based on the user's tracking preferences. If they are not removed
there is a risk the user's tracking preferences will not be honoured.

Configure your service's GTM container in `conf/application.conf`. For example, if you have been
instructed to use GTM container `a`, the configuration would appear as:

```
tracking-consent-frontend {
  gtm.container = "a"
}
```

`gtm.container` can be one of: `a`, `b`, `c`, `d`, `e`, `f` or `sdes`. Consult with the CIPSAGA team 
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

Add the library to the project dependencies:

``` scala~~~~
libraryDependencies += "uk.gov.hmrc" %% "play-language" % "[INSERT VERSION]"
```

Ensure to add the resolvers to your `plugins.sbt`:

```scala
resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns)

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "[INSERT VERSION 3.0.0 OR HIGHER]")
```

If you require either [Head](src/main/twirl/uk/gov/hmrc/play/views/layouts/Head.scala.html) or [Footer](src/main/twirl/uk/gov/hmrc/play/views/layouts/Footer.scala.html) you'll also need to add some config to your `application.conf` file in order to build the complete urls for assets:

```
assets {
  version = "x.x.x"
  url = ""
}
```

### Play Framework and Scala compatibility notes

This library is currently compatible with:

- Scala 2.12
- Play 2.6, Play 2.7, Play 2.8

And with the addition of support for Play 2.8 we have had to remove compatibility with Scala 2.11.

Additionally, the deprecated static helpers for components available in versions targeting up to Play 2.7 have been excluded from versions targeting Play 2.8. When upgrading your service to Play 2.8 you will now need to include components via [Dependency Injection within templates](https://www.playframework.com/documentation/2.8.x/ScalaTemplatesDependencyInjection) instead. 

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
