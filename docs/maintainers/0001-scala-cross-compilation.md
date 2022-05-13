# play-ui scala 2.13 cross compilation

* Status: accepted
* Date: 2022-05-30

## Context and Problem Statement

The need to allow scala cross compilation for scala version 2.13

## Decision Drivers

* as new scala versions are released we are in the process of allowing cross compilation between these new scala versions,
  for all our libraries and services so when service teams that use our libraries/services update their scala versions our libraries/services will support it.
* we don't want new services to start using play-ui
* we want to be able to easily change our mind if we need to

## Considered Options

* Do not allow scala cross compilation for scala version 2.13
* Add scala cross compilation for scala version 2.13

## Decision Outcome

Chosen option "Add scala cross compilation" because contact-frontend has a dependency on play-ui for its partial views and contact-frontend supports scala cross compilation for scala v2.13.
### Positive Consequences

* We are able to support our contact-frontend service as it has scala cross compilation and has play-ui as a dependency.

### Negative Consequences

* As adding scala cross compilation for version 2.13 would be implicitly supporting this deprecated library, we would not want to give service teams that are still using this library the wrong impression that we are still actively supporting it.
* By adding scala cross compilation for version 2.13, we could be creating more work for ourselves as there could be issues when service teams still actively using this library upgrade there scala version to 2.13. We would end up supporting those queries which we shouldn't do as we do not actively support this deprecated library anymore.
