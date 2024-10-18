#bin/bash

(cd cards && ./gradlew jibDockerBuild)
(cd docker-compose/default && docker-compose up -d cards)