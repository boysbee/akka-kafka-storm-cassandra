import scala.concurrent.Future
import scala.io.StdIn
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

object WebServer {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    val route: Route =
      get {
        pathPrefix("hello") {
          complete("ok")
        }
      }

    println(s"Server online at http://localhost:9090/")
    Http().bindAndHandle(route, "0.0.0.0", 9090)

  }
}
