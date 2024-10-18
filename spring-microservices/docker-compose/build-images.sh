#bin/bash

(cd config && ./gradlew jibDockerBuild)
(cd accounts && ./gradlew jibDockerBuild)
(cd loans && ./gradlew jibDockerBuild)
(cd cards && ./gradlew jibDockerBuild)