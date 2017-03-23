import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import config.Config._
import domain.Event
import kafka.KafkaMessageProducer
import spray.json.DefaultJsonProtocol._
import spray.json._

object WebServer {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    implicit val eventFormat = jsonFormat5(Event)
    
    val kafkaProducer = new KafkaMessageProducer

    val route: Route =
      get {
        pathPrefix("ping") {
          complete("ok")
        }
      } ~
      post {
        pathPrefix("event") {
          entity(as[Event]) { event =>
            kafkaProducer.createKafkaMessage(event.toJson.compactPrint)
            complete("Message successfully sent to Kafka")
          }
        }
      }

    println(s"Server online at http://localhost:9090/")
    Http().bindAndHandle(route, ServerHost, ServerPort)

  }
}
