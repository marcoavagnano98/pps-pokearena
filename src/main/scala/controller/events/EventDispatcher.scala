package controller.events

import controller.{BattleController, GameController, MenuController}
import controller.events.Event
import model.battle.{Battle, BattleOption}
import model.entities.pokemon.Pokemon
import model.entities.{Entity, Player, Trainer}
import pokearena.PokeArena
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}

import scala.collection.mutable
import scala.collection.mutable.Queue

object EventDispatcher:
  val eventQueue: mutable.Queue[Event] = mutable.Queue.empty

  def addEvent(events: Event*): Unit =
    eventQueue.addAll(events)

  def dispatch(): Unit =
    if eventQueue.nonEmpty then
      eventQueue.dequeue() match
          case e: MenuEvent => MenuController.eventHandler(e)
          case e: BattleEvent => BattleController.eventHandler(e)
          case e: GameEvent => GameController.eventHandler(e)