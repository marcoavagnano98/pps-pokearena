package model.entities.pokemon

import model.entities.pokemon.Pokemon
import util.Utilities.*
import scala.util.Random

trait PokemonStatus:
  /**
   * @return the name of the [[PokemonStatus]]
   */
  def name: String

  /**
   * @return the description of the [[PokemonStatus]]
   */
  def description: String

trait PokemonStatusWithEffect extends PokemonStatus :
  /**
   * @return the probability To Apply Status to a [[Pokemon]]
   */
  def probabilityToApplyStatus: Int

  /**
   * @param pokemon The [[Pokemon]] to which the [[PokemonStatusWithEffect]] is applied.
   * @return The new [[Pokemon]] with the [[PokemonStatusWithEffect]] updated
   */
  def applyStatus(pokemon: Pokemon): Pokemon = if Random.dice(probabilityToApplyStatus) then pokemon withStatus this else pokemon

  /**
   * @return true if the dice roll have success otherwise return false
   */
  //protected def isApplicableStatus: Boolean = Random.dice(probabilityToApplyStatus)

import StatusEffects.*

object AllPokemonStatus:
  case class HealthyStatus(override val name: String = "Normal",
                           override val description: String = "Normal status") extends PokemonStatus

  case class BurnStatus(override val name: String = "Burn",
                        override val description: String = "Lose 30 hp every turn and reduce the atk") extends PokemonStatusWithEffect with DealDamageEffect with ChangeAtkEffect :
    override def atkToChange: Int = -10

    override def damageOverTime: Int = 30

    override def probabilityToApplyStatus: Int = 30

    override def applyStatus(pokemon: Pokemon): Pokemon =
      val newPokemon = super.applyStatus(pokemon)
      if newPokemon.status == this then applyChangeStat(newPokemon)
      else newPokemon

  case class ParalyzeStatus(override val name: String = "Paralyze",
                            override val description: String = "Possibility to skip the turn and decrease speed") extends PokemonStatusWithEffect with SkipTurnEffect with ChangeSpeedEffect :
    override def speedToChange: Int = -10

    override def probabilityToApplyStatus: Int = 30

    override def probabilityToApplySkipTurn: Int = 30

    override def applyStatus(pokemon: Pokemon): Pokemon =
      val newPokemon = super.applyStatus(pokemon)
      if newPokemon.status == this then applyChangeStat(newPokemon)
      else newPokemon

  case class FreezeStatus(override val name: String = "Freeze",
                          override val description: String = "Skip the turn") extends PokemonStatusWithEffect with SkipTurnEffect :
    override def probabilityToApplyStatus: Int = 30

    override def probabilityToApplySkipTurn: Int = 100

  case class PoisonStatus(override val name: String = "Poison",
                          override val description: String = "Inflict damage by poison") extends PokemonStatusWithEffect with DealDamageEffect :

    override def probabilityToApplyStatus: Int = 30

    override def damageOverTime: Int = 20

  enum Status(val name: String):
    case Burn extends Status("burn")
    case Paralyze extends Status("paralyze")
    case Freeze extends Status("freeze")
    case Poison extends Status("poison")


