package me.kkanojia.hipbot

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import me.kkanojia.hipbot.models.WebHook
import me.kkanojia.hipbot.serializers.JsonSupport
import me.kkanojia.hipbot.service.BotService

import scala.concurrent.duration._

object StartBot extends App with JsonSupport{

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(20 seconds)

  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)


  lazy val botService = new BotService


  val route =
    path("") {
      logger.info("Bot Called >>")
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Bot says hello</h1>"))
      } ~
        post {
          entity(as[WebHook]) { order =>
            logger.info("Received a message" + order.toString)
            val commandResult = botService.getCommandResponse(order)
            complete(commandResult)
          }
        }
    }

  val systemPort = System.getenv("PORT")
  val port = if(systemPort == null) config.getInt("http.port") else systemPort.toInt

  val bindingFuture = Http().bindAndHandle(route, config.getString("http.interface"), port)

  logger.info("Bot Started")

}

