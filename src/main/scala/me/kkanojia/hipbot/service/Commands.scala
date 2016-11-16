package me.kkanojia.hipbot.service

object Commands {
  val defaultMessage = "I am sorry I dont understand what you are saying. Have you heard of google? Please try using it. (badpokerface)"

  val botCommands = Map(
    "hello" -> "How are you today. Have a (cookie)",
    "Who are you".toLowerCase -> "I am a bot, I am here to assist you. (yey)",
    "What can you do".toLowerCase -> "Nothing as of now. But am learning to do things, try again in some days. (badpokerface)"
  )

}
