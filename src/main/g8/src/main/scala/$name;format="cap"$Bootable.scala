package $organization$.$name$

import akka.actor.ActorDSL._
import akka.actor.{Actor, ActorSystem, Props, ActorLogging, ActorRef}
import akka.io.IO
import scala.concurrent.Await 
import scala.concurrent.duration._
//import spray.can.Http
//import spray.http._
//import spray.routing._
//import spray.util._
import scaldi._

object $name;format="cap"$Bootable extends App {

  implicit val system = ActorSystem("$name$-system")


}
