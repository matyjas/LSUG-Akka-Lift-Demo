package lsug.model

import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.actor.Actor._

import dispatch._

case class DUser(name: String, comet: {def !(msg:Any):Unit})
case class DTags(tags: List[String])

object DTagActor {

  def anActor = actorOf[DTagActor].start

}

class DTagActor extends Actor {

  def receive = {
    
    case DUser(userName, comet) => 

      val http = new Http
      val req = :/("feeds.delicious.com") / "v2" / "rss" / "tags" / userName
      val tagTitles = http ( req <> { _ \\ "item" \\ "title"} )
      val tags = tagTitles map (_ text)
    
      comet ! DTags(tags toList)

    case _ => println("unknown message")
  }

}
