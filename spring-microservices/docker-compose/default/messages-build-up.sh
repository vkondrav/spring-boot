#bin/bash

(cd messages && ./gradlew jibDockerBuild) &&
(cd docker-compose/default && docker-compose up -d messages)