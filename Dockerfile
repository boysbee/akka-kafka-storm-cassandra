FROM anapsix/alpine-java:8_server-jre

RUN apk --update add curl

WORKDIR /

USER daemon

ADD target/akka-kafka-storm-cassandra-assembly-0.0.1.jar /app/server.jar

ADD entrypoint.sh /entrypoint.sh

EXPOSE 8090

EXPOSE 9090

ENTRYPOINT ["/entrypoint.sh"]

HEALTHCHECK CMD curl --fail http://localhost:8080/ping || exit 1
