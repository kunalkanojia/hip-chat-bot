package me.kkanojia.hipbot.serializers

import java.text.SimpleDateFormat

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.ext.JodaTimeSerializers
import org.json4s.{native, DefaultFormats, Formats}

trait JsonSupport extends Json4sSupport {

  implicit val serialization = native.Serialization

  implicit def json4sFormats: Formats = DefaultFormats ++ JodaTimeSerializers.all

}