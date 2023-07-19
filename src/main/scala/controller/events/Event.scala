package controller.events


import model.battle.BattleAction
import model.entities.VisibleEntity
import model.entities.pokemon.Pokemon
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}

trait Event

trait MenuEvent extends Event

trait GameEvent extends Event

trait BattleEvent extends Event

case class StartGame(list: Seq[Pokemon]) extends MenuEvent

case class EndBattle(trainerId: String) extends BattleEvent

case class OptionChosen(battleOption: BattleAction) extends BattleEvent

case class CollisionEvent(entity: VisibleEntity) extends GameEvent

