package model.battle
import model.entities.pokemon.Pokemon
import model.entities.pokemon.StatusEffects.{SkipTurnEffect,DealDamageEffect}

import scala.language.postfixOps

trait BattleUnit:
  def trainerRef: String

  def pokemon: Pokemon

  def battleTurnEvent: BattleTurnEvent

  /**
   *
   * @param pokemon
   * @return a copy of [[BattleUnit]] with pokemon update
   */
  def withPokemonUpdate(pokemon: Pokemon): BattleUnit

  /**
   *
   *
   * @return copy of [[BattleUnit]] with  [[battleTurnEvent]] modified if the pokemon have to skip the turn
   */
  def checkSkipStatus: BattleUnit

  /**
   *
   * @return copy of [[BattleUnit]] with damage status applied
   */
  def withDamageStatusApplied: BattleUnit
  /**
   *
   *@return copy of [[BattleUnit]] with  [[battleTurnEvent]] modified if the pokemon is defeated
   */
  def withDefeatChecked: BattleUnit

object BattleUnit:

  def apply(trainerRef: String, pokemon: Pokemon, battleTurnEvent: BattleTurnEvent): BattleUnit =
    BattleUnitImpl(trainerRef: String, pokemon: Pokemon, battleTurnEvent: BattleTurnEvent)

  private case class BattleUnitImpl(override val trainerRef: String, override val pokemon: Pokemon,
                                    override val battleTurnEvent: BattleTurnEvent) extends BattleUnit:

    import BattleTurnEvent.*

    override def withPokemonUpdate(pokemon: Pokemon): BattleUnit =
      copy(pokemon = pokemon) withDefeatChecked

    override def checkSkipStatus: BattleUnit =
      pokemon.status match
        case s: SkipTurnEffect if s applyEffect pokemon => copy(battleTurnEvent = Skip)
        case _ => copy()

    override def withDamageStatusApplied: BattleUnit =
      pokemon.status match
        case s: DealDamageEffect => withPokemonUpdate(s.applyEffect(pokemon)) withDefeatChecked
        case _ => copy()


    override def withDefeatChecked: BattleUnit =
      pokemon.hp match
        case value: Int if value <= 0 => copy(battleTurnEvent = Defeat)
        case _ => copy()