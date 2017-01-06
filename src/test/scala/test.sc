import java.util.{Date, Properties}

import edu.stanford.nlp.ling.CoreAnnotations._
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import org.joda.time.DateTime

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

val stopWords = Set("i", "a", "about", "an", "are", "as", "at", "be", "by", "com", "for", "from", "how", "in", "is",
  "it", "of", "on", "or", "that", "the", "this", "to", "was", "what", "when", "where", "who", "will", "with")

val props = new Properties()
props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref")
val pipeline = new StanfordCoreNLP(props)

private def plainTextToLemmas(text: String) = {
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

val sentence = "set reminder to build something after 2 hrs 30 mins"

val intents = plainTextToLemmas(sentence).mkString("\n")





