package model.battle
import model.entities.Item
import model.entities.pokemon.{Move, Pokemon}
import model.entities.pokemon.StatusEffects.{DealDamageEffect, SkipTurnEffect}

import scala.language.postfixOps

enum TurnEvent(val description: String, val priority: Int):
  case Attack(move: Move) extends TurnEvent("usa " + move.name, 0)
  case UseBag(item: Item) extends TurnEvent("usa lo strumento " + item.name, 1)
  case Skip extends TurnEvent("salta il turno", 0)
  case Defeat extends TurnEvent("e' stato sconfitto", 0)

/** Maintains the information of pokemon during the [[Battle]] and keeps track of the events that happens during the [[Turn]]*/
trait Turn:
  def trainerRef: String

  def pokemon: Pokemon

  def battleTurnEvent: TurnEvent

  /**
   *
   * @param pokemon
   * @return a copy of [[Turn]] with pokemon update
   */
  def withPokemonUpdate(pokemon: Pokemon): Turn

  /**
   *
   *
   * @return copy of [[Turn]] with  [[battleTurnEvent]] modified if the pokemon have to skip the turn
   */
  def checkSkipStatus: Turn

  /**
   *
   * @return copy of [[Turn]] with damage status applied
   */
  def withDamageStatusApplied: Turn
  /**
   *
   *@return copy of [[Turn]] with  [[battleTurnEvent]] modified if the pokemon is defeated
   */
  def withDefeatChecked: Turn

object Turn:

  def apply(trainerRef: String, pokemon: Pokemon, battleTurnEvent: TurnEvent): Turn =
    TurnImpl(trainerRef: String, pokemon: Pokemon, battleTurnEvent: TurnEvent)

  private case class TurnImpl(override val trainerRef: String, override val pokemon: Pokemon,
                              override val battleTurnEvent: TurnEvent) extends Turn:

    import TurnEvent.*

    override def withPokemonUpdate(pokemon: Pokemon): Turn =
      copy(pokemon = pokemon) withDefeatChecked

    override def checkSkipStatus: Turn =
      pokemon.status match
        case s: SkipTurnEffect if s applyEffect pokemon => copy(battleTurnEvent = Skip)
        case _ => copy()

    override def withDamageStatusApplied: Turn =
      pokemon.status match
        case s: DealDamageEffect => withPokemonUpdate(s.applyEffect(pokemon)) withDefeatChecked
        case _ => copy()


    override def withDefeatChecked: Turn =
      pokemon.hp match
        case value: Int if value <= 0 => copy(battleTurnEvent = Defeat)
        case _ => copy()