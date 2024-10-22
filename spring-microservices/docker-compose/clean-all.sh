#bin/bash

(cd eureka && ./gradlew clean) &
(cd config && ./gradlew clean) &
(cd edge && ./gradlew clean) &
(cd accounts && ./gradlew clean) &
(cd loans && ./gradlew clean) &
(cd cards && ./gradlew clean) &

wait

docker images -f "dangling=true" -q | xargs docker rmi