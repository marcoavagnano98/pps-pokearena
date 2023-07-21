package controller.events

import com.badlogic.gdx.graphics.VertexAttribute.Position
import controller.{BattleController, GameController, MenuController}
import model.battle.{Battle, BattleTurnEvent}
import model.entities.pokemon.{Pokemon, PokemonFactory}
import model.entities.{Entity, Player, Trainer, World}
import pokearena.PokeArena
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}

import scala.collection.mutable
import scala.collection.mutable.Queue

trait EventDispatcher:
  val eventQueue: mutable.Queue[Event] = mutable.Queue.empty

  def sendEvent(events: Event*): Unit =
    eventQueue.addAll(events)

  def dispatch(): Unit =
    if eventQueue.nonEmpty then
      eventQueue.dequeue() match
          case e: MenuEvent => MenuController.eventHandler(e)
          case e: BattleEvent => BattleController.eventHandler(e)
          case e: GameEvent => GameController.eventHandler(e)