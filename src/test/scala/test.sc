val plainText =  "can you tell me whats the weather outside"

val stopWords = Set("i", "a", "about", "an", "are", "as", "at", "be", "by", "com", "for", "from", "how", "in", "is", "it", "of", "on", "or", "that", "the", "this", "to", "was", "what", "when", "where", " who ", "will", "with")

import java.util.Properties

import edu.stanford.nlp.pipeline._
import edu.stanford.nlp.ling.CoreAnnotations._

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

def plainTextToLemmas(text: String, stopWords: Set[String]): Seq[String] = {
  val props = new Properties()
  props.put("annotators", "tokenize, ssplit, pos, lemma")
  val pipeline = new StanfordCoreNLP(props)
  val doc = new Annotation(text)
  pipeline.annotate(doc)
  val lemmas = new ArrayBuffer[String]()
  val sentences = doc.get(classOf[SentencesAnnotation])
  for (sentence <- sentences; token <- sentence.get(classOf[TokensAnnotation])) {
    val lemma = token.get(classOf[LemmaAnnotation])
    if (lemma.length > 1 && !stopWords.contains(lemma)) {
      lemmas += lemma.toLowerCase
    }
  }
  lemmas
}

val lemmatized = plainTextToLemmas(plainText, stopWords)
