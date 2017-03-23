package storm.bolts

import cassandra.CassandraStorage
import org.apache.storm.topology.base.BaseBasicBolt
import org.apache.storm.topology.{BasicOutputCollector, OutputFieldsDeclarer}
import org.apache.storm.tuple.{Fields, Tuple}
import spray.json.DefaultJsonProtocol._
import spray.json._
import domain.Event
import handler.StatsHandler
class StatsBolt extends BaseBasicBolt {

  override def execute(input: Tuple, collector: BasicOutputCollector): Unit = {
    implicit val eventFormat = jsonFormat5(Event)
    val event = new String(input.getBinary(0)).parseJson.convertTo[Event]
    val handler = new StatsHandler(CassandraStorage)
    handler.trackEvent(event)
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("word"))
  }
}