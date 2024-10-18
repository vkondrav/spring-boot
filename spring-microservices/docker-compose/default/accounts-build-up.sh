#bin/bash

(cd accounts && ./gradlew jibDockerBuild)
(cd docker-compose/default && docker-compose up -d accounts)