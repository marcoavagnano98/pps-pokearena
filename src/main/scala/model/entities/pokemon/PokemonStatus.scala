package model.entities.pokemon

import model.entities.pokemon.Pokemon
import util.Utilities.*
import scala.util.Random

trait PokemonStatus:
  def name: String

  def description: String

trait PokemonStatusWithEffect extends PokemonStatus :
  def probabilityToApplyStatus: Int

  def applyStatus(p: Pokemon): Pokemon =
    if Random.dice(probabilityToApplyStatus) then
      p withStatus this
    else
      p

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
      applyChangeStat(super.applyStatus(pokemon))


  case class ParalyzeStatus(override val name: String = "Paralyze",
                            override val description: String = "Possibility to skip the turn and decrease speed") extends PokemonStatusWithEffect with SkipTurnEffect with ChangeSpeedEffect :
    override def speedToChange: Int = -10

    override def probabilityToApplyStatus: Int = 30

    override def probabilityToApplySkipTurn: Int = 30

    override def applyStatus(pokemon: Pokemon): Pokemon =
      applyChangeStat(super.applyStatus(pokemon))

  case class FreezeStatus(override val name: String = "Freeze",
                          override val description: String = "Skip the turn") extends PokemonStatusWithEffect with SkipTurnEffect :
    override def probabilityToApplyStatus: Int = 30

    override def probabilityToApplySkipTurn: Int = 100

  case class PoisonStatus(override val name: String = "Poison",
                          override val description: String = "Inflict damage by poison") extends PokemonStatusWithEffect with DealDamageEffect :

    override def probabilityToApplyStatus: Int = 30

    override def damageOverTime: Int = 20

  enum Status(val name:String):
    case Burn extends Status("burn")
    case Paralyze extends Status("paralyze")
    case Freeze extends Status("freeze")
    case Poison extends Status("poison")


