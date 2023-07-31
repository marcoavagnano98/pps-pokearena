package controller.events

import com.badlogic.gdx.graphics.VertexAttribute.Position
import controller.{BattleController, GameController, MenuController}
import model.battle.{Battle, Status}
import model.entities.pokemon.Pokemon
import model.entities.{Entity, Player, Trainer, World}
import pokearena.PokeArena
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}

import scala.collection.mutable
import scala.collection.mutable.Queue

/**
 * Manages the dispatch of events to the appropriate Controller
 */
trait EventDispatcher:
  val eventQueue: mutable.Queue[Event] = mutable.Queue.empty

  /**
   * Adds the input event/events to the event queue
   * @param events the event/events to add in the queue
   */
  def sendEvent(events: Event*): Unit =
    eventQueue.addAll(events)

  /**
   * Send the event to the appropriate Controller's eventHandler
   */
  def dispatch(): Unit =
    if eventQueue.nonEmpty then
      eventQueue.dequeue() match
          case e: MenuEvent => MenuController.eventHandler(e)
          case e: BattleEvent => BattleController.eventHandler(e)
          case e: GameEvent => GameController.eventHandler(e)