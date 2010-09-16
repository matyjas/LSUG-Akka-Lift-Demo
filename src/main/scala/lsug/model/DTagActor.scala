package lsug.model

import se.scalablesolutions.akka.actor.{Actor,Supervisor}
import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.config.ScalaConfig._

import dispatch._

case class DUser(name: String, comet: {def !(msg:Any):Unit})
case class DTags(tags: List[String])

object DTagActor {

  val supervisor = Supervisor(
  SupervisorConfig(
    RestartStrategy(OneForOne, 3, 1000, List(classOf[Exception])),
    Supervise(
      actorOf[DTagActor],
      LifeCycle(Permanent)) ::
    Nil))

  def anActor = {
    val dta = actorOf[DTagActor].start
    supervisor.link(dta)
    dta
  }

}

class DTagActor extends Actor {

  self.lifeCycle = Some(LifeCycle(Permanent))

  var client: Option[{def !(msg:Any):Unit}] = None

  override def preRestart(reason: Throwable) = {

    client foreach (_ ! DTags(Nil))
  }

  def receive = {
    
    case DUser(userName, comet) => 

      client = Some(comet)
      val http = new Http
      val req = :/("feeds.delicious.com") / "v2" / "rss" / "tags" / userName
      val tagTitles = http ( req <> { _ \\ "item" \\ "title"} )
      val tags = tagTitles map (_ text)
    
      comet ! DTags(tags toList)

    case _ => println("unknown message")
  }

}
