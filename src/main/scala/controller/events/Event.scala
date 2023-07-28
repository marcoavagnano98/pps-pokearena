package controller.events

import model.battle.TrainerChoice
import model.entities.VisibleEntity
import model.entities.pokemon.Pokemon
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}

trait Event

trait MenuEvent extends Event

trait GameEvent extends Event

trait BattleEvent extends Event

case class StartGame(list: Seq[Pokemon]) extends MenuEvent

case class PokemonDefeated() extends BattleEvent

case class OptionChosen(battleOption: TrainerChoice) extends BattleEvent

case class CollisionEvent(entity: VisibleEntity) extends GameEvent

case class EndGame() extends GameEvent
