#bin/bash

(cd accounts && ./gradlew jibDockerBuild)
(cd loans && ./gradlew jibDockerBuild)
(cd cards && ./gradlew jibDockerBuild)

docker-compose up -d