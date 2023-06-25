package model.entities.pokemon

import model.entities.pokemon.Pokemon
import util.Utilities.*
import scala.util.Random

trait PokemonStatus:
  def name:String
  def description:String

trait PokemonStatusWithEffect extends PokemonStatus :
  def probabilityToApplyStatus: Int

  def applyStatus(p: Pokemon): Pokemon

import StatusEffects.*

object AllPokemonStatus:
  case class HealthyStatus(override val name:String = "Normal",
                      override val description:String = "Normal status") extends PokemonStatus

  case class BurnStatus(override val name:String = "Burn",
                   override val description:String = "Lose 30 hp every turn and reduce the atk") extends PokemonStatusWithEffect with DealDamageEffect with ChangeAtkEffect :
    override def atkToChange: Int = 10

    override def quantityOfDamage: Int = 30

    override def probabilityToApplyStatus: Int = 30

    override def applyStatus(pokemon: Pokemon): Pokemon = if (Random.dice(probabilityToApplyStatus)) {
      changeStatsEffect(pokemon, atkToChange) withStatus this
    } else {
      pokemon
    }

  case class ParalyzeStatus(override val name:String = "Paralyze",
                       override val description:String = "Possibility to skip the turn and decrease speed") extends PokemonStatusWithEffect with SkipTurnEffect with ChangeSpeedEffect :
    override def speedToChange: Int = 10
    override def probabilityToApplyStatus: Int = 30
    override def probabilityToApplySkipTurn: Int = 30

    override def applyStatus(pokemon: Pokemon): Pokemon = if (Random.dice(probabilityToApplyStatus)) {
      changeStatsEffect(pokemon, speedToChange) withStatus this
    } else {
      pokemon
    }
