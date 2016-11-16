package me.kkanojia.hipbot.models


case class WebHook(
  event: String,
  item: WebHookRoomMessageItem,
  oauth_client_id: Option[String],
  webhook_id: Long)

case class WebHookRoomMessageItem(message: WebHookMessage, room: Option[RoomsItem])

case class WebHookMessage(
  date: String,
  file: Option[HCFile],
  from: From,
  id: String,
  mentions: Option[Seq[MentionItem]],
  message: String)

case class HCFile(name: String, size: Long, url: String)

case class From(id: Long, links: Option[FromLinks], mention_name: String, name: String)

case class FromLinks(self: String)

case class RoomsItem(id: Long, links: Option[RoomsItemLinks], name: String)

case class RoomsItemLinks(self: String, webhooks: String, members: Option[String])

case class MentionItem(id: Long, links: MentionLinks, mention_name: String, name: String)

case class MentionLinks(self: String)
