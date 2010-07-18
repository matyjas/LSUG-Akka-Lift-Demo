package lsug.comet

import scala.xml.{NodeSeq}

import net.liftweb.http.{CometActor,SHtml}
import net.liftweb.http.js.JsCmds.{Noop}

import lsug.model._

class DCometActor extends CometActor {

  var user: Option[String] = None
  var tags: List[String] = Nil
  val tagActor = DTagActor anActor

  def render = bind ("user" -> renderUserNameEntry,
                     "tags" -> renderTags)

  def renderTags : NodeSeq = {

    tags map (t => <p>{t}</p>)
  }

  def renderUserNameEntry = {

    def userName = user getOrElse "del.User.name"

    def updateUserName(newName: String) = {

      tagActor ! DUser(newName, this)
      user = Some(newName)
      Noop
    }

    <span>
{    SHtml.ajaxText(userName,
                    {updateUserName _})}
      </span>
  }

  override def lowPriority = {

    case DTags(tgs) =>

      tags = tgs
      reRender(false)
  }
}
