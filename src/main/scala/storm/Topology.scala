package storm

import config.Config
import org.apache.storm.kafka.{KafkaSpout, SpoutConfig, ZkHosts}
import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.{StormSubmitter, Config => StormConfig}
import storm.bolts.{CounterBolt, StatsBolt}
// import storm.bolts.{CounterBolt, ItemItemBolt, StatsBolt, TrendingBolt}


object Topology {

  def main(args: Array[String]): Unit = {

    val kafkaSpout = createKafkaSpout(Config.ZooKeeperHost, Config.Topic, Config.ZkSpoutId)

    val builder = new TopologyBuilder()
    builder.setSpout("kafka_spout", kafkaSpout)
    builder.setBolt("counter_bolt", new CounterBolt()).shuffleGrouping("kafka_spout")
    builder.setBolt("stats_bolt", new StatsBolt()).shuffleGrouping("kafka_spout")
    val config = new StormConfig()
    config.setDebug(true)

    StormSubmitter.submitTopology("myweb-analytic", config, builder.createTopology())
  }

  def createKafkaSpout(zkString: String, topic: String, zkSpoutId: String): KafkaSpout = {
    val zkConnString = zkString
    val zkHosts = new ZkHosts(zkConnString)
    val spoutConfig = new SpoutConfig(zkHosts, topic, "/" + topic, zkSpoutId)
    val kafkaSpout = new KafkaSpout(spoutConfig)
    kafkaSpout
  }

}
