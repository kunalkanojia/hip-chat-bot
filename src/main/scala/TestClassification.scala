import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations._
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

object TestClassification extends App{

  val stopWords = Set("i", "a", "about", "an", "are", "as", "at", "be", "by", "com", "for", "from", "how", "in", "is",
    "it", "of", "on", "or", "that", "the", "this", "to", "was", "what", "when", "where", "who", "will", "with")

  val reminderWords = Seq("remind", "reminder")

  val timeNameEntities = Seq("DATE", "DURATION", "TIME")

  val props = new Properties()
  props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref")
  val pipeline = new StanfordCoreNLP(props)

  private def textToLemmaPosNe(text: String): Seq[(String, String, String)] = {
    val doc = new Annotation(text)
    pipeline.annotate(doc)
    val lemmas = new ArrayBuffer[(String, String, String)]()
    val sentences = doc.get(classOf[SentencesAnnotation])

    for (sentence <- sentences;
         token <- sentence.get(classOf[TokensAnnotation])
    ) {

      val lemma = token.get(classOf[LemmaAnnotation])
      // this is the POS tag of the token
      val pos = token.get(classOf[PartOfSpeechAnnotation])
      // this is the NER label of the token
      val ne = token.get(classOf[NamedEntityTagAnnotation])

      if (!stopWords.contains(lemma)) {
        lemmas += ((lemma.toLowerCase, pos, ne))
      }
    }
    lemmas
  }

  private def identifyIntent(text: String) = {
    val tags =  textToLemmaPosNe(sentence)
    val lemmas = tags.map(_._1)

    if(lemmas.intersect(reminderWords).nonEmpty){
      println("Its some sort of a reminder")

      val timing = tags.filter(x => timeNameEntities.contains(x._3)).map(x => (x._1, x._3))
      println("timings in the sentence are => " + timing)

      val exactTime = TimeEvaluator.evaluateTime(timing)
      println("Reminder time - "+ exactTime)

      val filteredVerbs = tags.filter(_._2.contains("VB")).map(_._1).filterNot(reminderWords.contains(_))
      filteredVerbs.headOption match {
        case Some(verb) =>
          println("reminder is => " + text.substring(text.indexOf(verb)))

        case None =>
          println("Reminder is => " + text)
      }

    } else if(lemmas.contains("execute")){
      println("its an execute command")
    }

  }

  val sentence = "set reminder to build something something on 29 Dec"

  identifyIntent(sentence)


}
