package fibonacci

import akka.actor.{ActorSystem, Props}
import akka.serialization.SerializationExtension
import akka.testkit.{ImplicitSender, TestKit}
import fibonacci.serialization.FibonacciState.GeneratorState
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
      fibo ! ResetGenerator
      fibo ! GetNextNumber
      expectMsg(1L)
      fibo ! GetNextNumber
      expectMsg(2L)
      fibo ! GetNextNumber
      expectMsg(3L)
      fibo ! GetNextNumber
      expectMsg(5L)
      fibo ! GetNextNumber
      expectMsg(8L)
    }
  }
}
