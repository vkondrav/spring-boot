#bin/bash

(cd eureka && ./gradlew jibDockerBuild)
(cd config && ./gradlew jibDockerBuild)
(cd accounts && ./gradlew jibDockerBuild)
(cd loans && ./gradlew jibDockerBuild)
(cd cards && ./gradlew jibDockerBuild)

docker images -f "dangling=true" -q | xargs docker rmi