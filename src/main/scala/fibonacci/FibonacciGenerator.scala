package fibonacci

import akka.actor.Actor
import akka.persistence.{PersistentActor, SnapshotOffer}
import fibonacci.serialization.FibonacciState.GeneratorState

object FibonacciGeneratorProtocol {

  case object GetNextNumber

  case object ResetGenerator

}

class FibonacciGenerator extends PersistentActor {

  import FibonacciGeneratorProtocol._

  var state: GeneratorState = GeneratorState(0, 1)

  def updateState(event: GeneratorState): Unit = {
    state = event
  }

  def receiveCommand = {
    case GetNextNumber => {
      val fn = state.f0 + state.f1
      state = GeneratorState(state.f1, fn)
      persist(state) { event =>
        sender() ! fn
        saveSnapshot(event)
      }
    }
    case ResetGenerator => {
      state = GeneratorState(0, 1)
      persist(state) {
        //saveSnapshot(_)
        _ => Unit
      }
    }
  }

  override def receiveRecover: Receive = {
    case evt: GeneratorState => {
      updateState(evt)
    }
    case SnapshotOffer(_, snapshot: GeneratorState) => state = snapshot
  }

  override def persistenceId: String = "fibonacci"
}
