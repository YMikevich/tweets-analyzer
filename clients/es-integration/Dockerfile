FROM hirokimatsumoto/alpine-openjdk-11

RUN apk update && apk add bash

VOLUME /tmp
ADD ./build/libs/es-integration-0.0.1-SNAPSHOT.jar es-integration.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/es-integration.jar"]