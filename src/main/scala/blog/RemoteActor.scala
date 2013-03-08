package blog


import com.typesafe.config.ConfigFactory

import akka.actor._


object instantiateRemoteActor extends App {

  val configString = """akka {actor {
                provider = "akka.remote.RemoteActorRefProvider" }
             remote {netty { hostname = "HOST"}}}
              akka {
             remote.netty.port = PORT}"""
  val config = configString.replaceAll("HOST", "127.0.0.1").replaceAll("PORT", "2559")
  val customConf = ConfigFactory.parseString(config)
  val remoteSystem = ActorSystem("RemoteApplication", ConfigFactory.load(customConf))
  // val system = ActorSystem("listeners")
  //  val remoteActor = system.actorOf(Props(new RemoteActor).withDispatcher("dpu-dispatcher"))
  val remoteIndexerListener = remoteSystem.actorOf(Props[RemoteListener], "remote")

}

class RemoteListener extends Actor {
  def receive = {

    case msg: String =>
      println(msg)
      sender ! "I am fine"

  }