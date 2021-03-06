package com.frankandrobot.chen


package object DocTypes {

  /**
    * Can be anything that uniquely identifies it, like a URL
    *
    * @param id
    */
  case class DocId(val id : String)

  case class RawDoc(val docId : DocId,
                    val title : String,
                    val contents : String) {

    def toDocLite() = {
      DocLite(
        docId,
        title,
        contents.length match {
          case n if n >= 140 => contents.substring(0, 140)
          case _ => contents
        }
      )
    }
  }

  case class DocLite(val docId : DocId, val title : String, val blurb : String)

  class RawTerm(val value : String) {

    override def toString: String = value
  }

  object RawTerm {
    def apply(value : String) = new RawTerm(value)
  }

  /**
    * When you put these in a list, you get the raw terms grouped by doc
    *
    * @param doc
    * @param terms
    */
  case class Doc(val doc : DocLite,
                 val terms : List[RawTerm],
                 val histogram : collection.Map[String, Int])

  case class Term(val index : Int, override val value : String, val docs : Set[DocLite]) extends RawTerm(value)
}
