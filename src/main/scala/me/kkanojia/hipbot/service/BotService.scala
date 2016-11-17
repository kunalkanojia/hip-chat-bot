package me.kkanojia.hipbot.service

import me.kkanojia.hipbot.models.{RoomNotification, WebHook, WebHookMessage}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class BotService {

  def getCommandResponse(webHook: WebHook): Future[RoomNotification] = {
    Future {
      mapCommandToResponse(webHook.item.message)
    }
  }

  def mapCommandToResponse(message: WebHookMessage): RoomNotification = {

    val userName = message.from.name
    val command = message.message.replace("/bot", "")
    val languageService = new LanguageService
    val importantWords = languageService.getTagsFromMessage(command)
    val response = "These are the important words from the sentence - " + importantWords.mkString("[", ",", "]")

    RoomNotification(
      color = "green",
      message =  s"Hello $userName. $response",
      _notify = true,
      message_format = "text"
    )
  }

}
