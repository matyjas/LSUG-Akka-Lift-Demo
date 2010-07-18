import sbt._

class LSUGAkkaLiftProject(info: ProjectInfo) extends DefaultWebProject(info) {

  // Akka
  val akkaRepo = "Akka Maven Repository" at "http://scalablesolutions.se/akka/repository"
  val multiverseRepo = "Codehaus Maven Repository" at "http://snapshots.repository.codehaus.org"
  val akkaCoreDep = "se.scalablesolutions.akka" % "akka-core_2.8.0.RC3" % "0.10" % "compile"

  // Lift Repos
  val snapshots = ScalaToolsSnapshots
  // required because Ivy doesn't pull repositories from poms
  val smackRepo = "m2-repository-smack" at "http://maven.reucon.com/public"
  val nexusRepo = "nexus" at "https://nexus.griddynamics.net/nexus/content/groups/public" 

  // Lift Dependencies
  val lift = "net.liftweb" % "lift-core" % "2.0-scala280-SNAPSHOT" % "compile"
  val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.14" % "test"
  val servlet = "javax.servlet" % "servlet-api" % "2.5" % "provided"
  val derby = "org.apache.derby" % "derby" % "10.2.2.0" % "runtime"
  val junit = "junit" % "junit" % "3.8.1" % "test"
}
