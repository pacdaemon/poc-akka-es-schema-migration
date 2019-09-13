package fibonacci

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class FibonacciGeneratorSpec
  extends TestKit(ActorSystem("FibonacciSpec"))
    with ImplicitSender
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "A Fibonacci Generator actor" must {
    "get the numbers in the expected secuence" in {
      import FibonacciGeneratorProtocol._
      val fibo = system.actorOf(Props[FibonacciGenerator])
      fibo ! GetNextNumber
      expectMsg(BigInt(1))
      fibo ! GetNextNumber
      expectMsg(BigInt(2))
      fibo ! GetNextNumber
      expectMsg(BigInt(3))
      fibo ! GetNextNumber
      expectMsg(BigInt(5))
      fibo ! GetNextNumber
      expectMsg(BigInt(8))
    }
  }
}
