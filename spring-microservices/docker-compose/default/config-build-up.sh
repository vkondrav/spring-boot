#bin/bash

(cd config && ./gradlew jibDockerBuild)
(cd docker-compose/default && docker-compose up -d config)