import org.joda.time.DateTime

object TimeEvaluator {

  private val todayStartOfDay = new DateTime().withHourOfDay(9).withMinuteOfHour(0).withSecondOfMinute(0)
  private val dat = Seq("day", "after", "tomorrow")

  private val monthsAbbr = Seq("jan", "feb", "mar", "apr", "may", "june", "july", "aug", "sept", "oct", "nov", "dec")
  private val months = Seq("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december")

  private val hrsAbbr = Seq("hour", "hours", "hrs")
  private val minAbbr = Seq("min", "mins", "minutes")
  private val secAbbr = Seq("sec", "secs", "seconds")

  def evaluateTime(timeIntents: Seq[(String, String)]): Option[DateTime] = {

    val timeEntities = timeIntents.map(_._1)

    val isDuration = timeIntents.exists(_._2 == "DURATION")
    val isTime = timeIntents.exists(_._2 == "TIME")

    if(isDuration){

      val allNumbers = timeEntities.filter(_.forall(_.isDigit))

    }


    val isDayAfterTomorrow = timeEntities.intersect(dat).nonEmpty
    val isTomorrow = timeEntities.contains("tomorrow")
    val isToday = timeEntities.contains("today") || (!isDayAfterTomorrow && !isTomorrow)

    val containsMonth = timeEntities.exists(entity => months.contains(entity) || monthsAbbr.contains(entity))

    val containsDateOrTime = timeEntities.exists(_.forall(_.isDigit))

    containsDateOrTime match {
      case false if isTomorrow => Some(todayStartOfDay.plusDays(1))
      case false if isDayAfterTomorrow => Some(todayStartOfDay.plusDays(2))
      case true => None
    }

  }

}
