#bin/bash

(cd edge && ./gradlew jibDockerBuild) &&
(cd docker-compose/default && docker-compose up -d edge)