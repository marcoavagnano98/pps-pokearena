package model.battle
import model.entities.pokemon.Pokemon
import model.entities.pokemon.StatusEffects.{SkipTurnEffect,DealDamageEffect}

import scala.language.postfixOps

trait Turn:
  def trainerRef: String

  def pokemon: Pokemon

  def battleTurnEvent: BattleTurnEvent

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

  def apply(trainerRef: String, pokemon: Pokemon, battleTurnEvent: BattleTurnEvent): Turn =
    TurnImpl(trainerRef: String, pokemon: Pokemon, battleTurnEvent: BattleTurnEvent)

  private case class TurnImpl(override val trainerRef: String, override val pokemon: Pokemon,
                              override val battleTurnEvent: BattleTurnEvent) extends Turn:

    import BattleTurnEvent.*

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