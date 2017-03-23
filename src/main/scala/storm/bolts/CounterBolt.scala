package storm.bolts

import cassandra.CassandraStorage
import domain.Event
import org.apache.storm.topology.base.BaseBasicBolt
import org.apache.storm.topology.{BasicOutputCollector, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}
import org.slf4j.LoggerFactory
import recommendation.engines.CounterPageEvents
import spray.json.DefaultJsonProtocol._
import spray.json._

class CounterBolt extends BaseBasicBolt {
  val Logger = LoggerFactory.getLogger(this.getClass)

  override def execute(input: Tuple, collector: BasicOutputCollector): Unit = {
    implicit val eventFormat = jsonFormat4(Event)
    val event = new String(input.getBinary(0)).parseJson.convertTo[Event]
    val counter = new CounterPageEvents(CassandraStorage)
    Logger.info(s"Receive $event")
    counter.trackEvent(event)
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("word"))
  }
}
