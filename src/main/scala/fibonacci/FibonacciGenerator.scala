package fibonacci

import akka.actor.Actor
import akka.persistence.{PersistentActor, SnapshotOffer}

object FibonacciGeneratorProtocol {
  case object GetNextNumber
  case class GeneratorState(f0: BigInt, f1: BigInt)
}

class FibonacciGenerator extends PersistentActor {
  import FibonacciGeneratorProtocol._

  var state: GeneratorState = GeneratorState(0,1)

  def receiveCommand = {
    case GetNextNumber => {
      val fn = state.f0 + state.f1
      state = GeneratorState(state.f1,fn)
      sender() ! fn
    }
  }

  override def receiveRecover: Receive = {
    case evt: GeneratorState => state = evt
    case SnapshotOffer(_, snapshot: GeneratorState) => state = snapshot
  }

  override def persistenceId: String = "fibonacci"
}
