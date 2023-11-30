resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
  Resolver.ivyStylePatterns
)

// required since we're cross building for Play 2.8 which isn't compatible with sbt 1.9
libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "3.6.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.5.1")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
