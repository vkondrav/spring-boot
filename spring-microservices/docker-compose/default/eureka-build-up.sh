#bin/bash

(cd eureka && ./gradlew jibDockerBuild) &&
(cd docker-compose/default && docker-compose up -d eureka)