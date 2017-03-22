#akka-kafka-storm-cassandra

## Build & Deploy to docker.

1.) Build jar file.
2.) Use docker-compose.

```sh
sbt clean 'set test in assembly := {}' assembly

docker-compose up
```
