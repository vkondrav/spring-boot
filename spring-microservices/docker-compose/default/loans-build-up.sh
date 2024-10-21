#bin/bash

(cd loans && ./gradlew jibDockerBuild) &&
(cd docker-compose/default && docker-compose up -d loans)