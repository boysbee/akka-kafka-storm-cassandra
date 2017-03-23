package cassandra

import com.websudos.phantom.dsl._
import config.Config

import scala.concurrent.Await
import scala.concurrent.duration._


object DefaultConnector {

  val hosts = Seq(Config.CassandraHost)

  val connector = ContactPoints(hosts).keySpace(Config.CassandraKeySpace)

}

class CassandraStorage(val keyspace: KeySpaceDef) extends Database(keyspace) {

  object userViews extends Views with keyspace.Connector
  object stats extends Stats with keyspace.Connector

}

object CassandraStorage extends CassandraStorage(DefaultConnector.connector) {
  Await.result(CassandraStorage.autocreate.future(), 5.seconds)
}


