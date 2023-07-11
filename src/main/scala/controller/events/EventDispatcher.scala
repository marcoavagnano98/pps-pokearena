package controller.events

import controller.{BattleController, GameController, MenuController}
import controller.events.Event
import model.battle.{Battle, BattleOption}
import model.entities.pokemon.Pokemon
import model.entities.{Entity, Player, Trainer}
import pokearena.PokeArena
import view.GameScreen
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}

import scala.collection.mutable
import scala.collection.mutable.Queue



object EventDispatcher:
  val eventQueue: mutable.Queue[Event] = mutable.Queue.empty

  def addEvent(e: Event): Unit =
    eventQueue += e

  def dispatchAll(): Unit =
    if eventQueue.nonEmpty then
      for e <- eventQueue
        do e match
          case _: MenuEvent => MenuController.eventHandler(e)
          case _: BattleEvent => BattleController.eventHandler(e)
          case _: GameEvent => GameController.eventHandler(e)
      eventQueue.dequeue()