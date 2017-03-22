#!/bin/sh

set -e

JAVA_OPTS=${JAVA_OPTS:="-Xmx128m -Xms128m -server -verbose:gc -XX:+UseG1GC -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote.port=9090 -Dcom.sun.management.jmxremote.rmi.port=9090 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"}

exec java -jar $JAVA_OPTS /app/server.jar
