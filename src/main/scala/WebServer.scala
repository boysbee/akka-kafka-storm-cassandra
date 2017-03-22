import scala.concurrent.Future
import scala.io.StdIn
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol._
import spray.json._
import config.Config._
import kafka.KafkaMessageProducer
import domain.Event

object WebServer {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    implicit val eventFormat = jsonFormat4(Event)
    
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
