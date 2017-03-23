package config

object Config {

  final val ZooKeeperHost = "zookeeper:2181"
  final val Topic = "tests"
  final val ZkSpoutId = "myweb-clickstream"
  final val CassandraHost = "cassandra"
  final val CassandraKeySpace = "myweb"
  final val ActionWeights = Map(
    "display" -> 0,
    "hover" -> 1,
    "click" -> 2,
    "like" -> 3,
    "share" -> 4,
    "buy" -> 5
    //    "0.5" -> 1,
    //    "1.0" -> 1,
    //    "1.5" -> 2,
    //    "2.0" -> 2,
    //    "2.5" -> 3,
    //    "3.0" -> 3,
    //    "3.5" -> 4,
    //    "4.0" -> 4,
    //    "4.5" -> 5,
    //    "5.0" -> 5
  )
  final val RecentViewsListSize = 100
  val KafkaProducerConfigLocation = "/kafka-0.10.0.1-producer-defaults.properties"
  val ServerHost = "0.0.0.0"
  val ServerPort = 8090
  val KafkaTopicName = "tests"
}
