package me.kkanojia.hipbot.service

import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations._
import edu.stanford.nlp.pipeline._

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

class LanguageService {

  val stopWords = Set("i", "a", "about", "an", "are", "as", "at", "be", "by", "com", "for", "from", "how", "in", "is",
    "it", "of", "on", "or", "that", "the", "this", "to", "was", "what", "when", "where", "who", "will", "with")

  val props = new Properties()
  props.put("annotators", "tokenize, ssplit, pos, lemma")
  val pipeline = new StanfordCoreNLP(props)

  def getTagsFromMessage(message: String): Seq[String] = {
     plainTextToLemmas(message, stopWords)
  }


  private def plainTextToLemmas(text: String, stopWords: Set[String]): Seq[String] = {
    val doc = new Annotation(text)
    pipeline.annotate(doc)
    val lemmas = new ArrayBuffer[String]()
    val sentences = doc.get(classOf[SentencesAnnotation])
    for (sentence <- sentences;
         token <- sentence.get(classOf[TokensAnnotation])
    ) {
      val lemma = token.get(classOf[LemmaAnnotation])
      if (lemma.length > 1 && !stopWords.contains(lemma)) {
        lemmas += lemma.toLowerCase
      }
    }
    lemmas
  }


}
