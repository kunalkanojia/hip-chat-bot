package me.kkanojia.hipbot.models

case class RoomNotification(
  color: String,
  message: String,
  _notify: Boolean,
  message_format: String
)
