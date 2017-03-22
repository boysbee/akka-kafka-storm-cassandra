name := "akka-kafka-storm-cassandra"

version := "0.0.1"

scalaVersion := "2.11.8"

crossPaths := false
unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil

val stormVersion = "1.0.2"
val zookeeperVesion = "3.4.9"
val kafkaVersion = "0.10.1.1"
val akkaHttpVersion = "10.0.0"
val scalatestVersion = "3.0.1"

resolvers ++= Seq(
  "Typesafe repository snapshots"    at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases"     at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  Resolver.bintrayRepo("websudos", "oss-releases")
)

libraryDependencies ++= Seq(
  "org.apache.zookeeper" % "zookeeper" % zookeeperVesion
    exclude("org.slf4j", "slf4j-log4j12"),
  "org.apache.kafka" %% "kafka" % "0.10.1.1" // 2.12 support
    exclude("org.slf4j", "slf4j-simple")
    exclude("org.slf4j", "slf4j-log4j12")
    exclude("log4j", "log4j")
    exclude("org.apache.zookeeper", "zookeeper"),
  "org.apache.storm" % "storm-core" % stormVersion % "provided"
    exclude("org.apache.zookeeper", "zookeeper")
    exclude("org.slf4j", "log4j-over-slf4j")
    exclude("org.apache.logging.log4j", "log4j-slf4j-impl"),
  "org.apache.storm" % "storm-kafka" % stormVersion
    exclude("org.apache.zookeeper", "zookeeper")
    exclude("org.apache.logging.log4j", "log4j-slf4j-impl"),
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.github.nscala-time" %% "nscala-time" % "2.14.0",
    "org.scalatest" %% "scalatest" % scalatestVersion % "test"
)

assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.startsWith("META-INF") => MergeStrategy.discard
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
  case PathList("org", "apache", xs @ _*) => MergeStrategy.first
  case PathList("org", "jboss", xs @ _*) => MergeStrategy.first
  case "about.html"  => MergeStrategy.rename
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}
