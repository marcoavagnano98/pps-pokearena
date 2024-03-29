package controller.events

import model.battle.TrainerChoice
import model.entities.VisibleEntity
import model.entities.pokemon.Pokemon
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}

/**
 * Represent the basic Event generated during the interaction with the Screens
 */
trait Event

/**
 * Type of Event generated during the interaction with [[PokemonChoiceScreen]]
 */
trait MenuEvent extends Event

/**
 * Type of Event generated during the interaction with [[GameScreen]]
 */
trait GameEvent extends Event

/**
 * Type of Event generated during the interaction with [[BattleScreen]]
 */
trait BattleEvent extends Event

/**
 * Event generated during the interaction with [[PokemonChoiceScreen]]
 * @param list the Sequence of Pokemon that compose the Player pokemon team
 */
case class StartGame(list: Seq[Pokemon], difficulty: Int) extends MenuEvent

/**
 * Event generated when a fight between two [[Pokemon]] is ended
 */
case class EndRound() extends BattleEvent

/**
 * Event generated to control the Battle options
 * @param battleOption the type of option selected during the Battle (Attack/UseBag)
 */
case class OptionChosen(battleOption: TrainerChoice) extends BattleEvent

/**
 * Event generated when the [[Player]] collide with another [[Entity]] in the GameScreen
 * @param entity with which a collision occurred
 */
case class CollisionEvent(entity: VisibleEntity) extends GameEvent

/**
 * Event generate when the Door needs to be opened: all the Trainers in the Level are defeated
 */
case class OpenDoor() extends GameEvent

/**
 * Event generate when the game needs to be ended
 */
case class DisplayGameOverScreen() extends GameEvent

/**
 * Event generate when the game ends
 */
case class ResetGame() extends  GameEvent