package model.entities.pokemon

import model.entities.pokemon.Pokemon
import util.Utilities.randomDice


/*

  trait PokemonStatus

  trait PokemonStatusWithEffect extend PokemonStatus


  NormalStatus
  Burn


  Effect

  Item
  pozione with ChangeStatsEffect


*/

//
import util.Utilities.randomDice

trait PokemonStatus

object AdditionalEffect:
  trait PermanentEffect:
    type Result

    def applyEffect(pokemon: Pokemon): Result

  trait SkipTurn extends PermanentEffect :
    override type Result = Boolean

    def probabilityToApplySkipTurn: Int

    override def applyEffect(pokemon: Pokemon): Result = randomDice(probabilityToApplySkipTurn)

  trait DealDamage extends PermanentEffect :
    def quantityOfDamage: Int

    override type Result = Pokemon

    override def applyEffect(pokemon: Pokemon): Result = pokemon withHp (pokemon.hp - quantityOfDamage)

  trait ChangeStatsEffect:
    def changeStatsEffect(pokemon: Pokemon, stat: Int): Pokemon

  case class ChangeHpEffect() extends ChangeStatsEffect :
    override def changeStatsEffect(pokemon: Pokemon, stat: Int): Pokemon =
      pokemon withHp stat

  trait ChangeAtkEffect extends ChangeStatsEffect :
    def atkToChange: Int

    override def changeStatsEffect(pokemon: Pokemon, stat: Int): Pokemon =
      pokemon withHp stat

  trait ChangeSpeedEffect extends ChangeStatsEffect :
    def speedToChange: Int

    override def changeStatsEffect(pokemon: Pokemon, stat: Int): Pokemon =
      pokemon withSpeed speedToChange


trait PokemonStatusWithEffect extends PokemonStatus :
  def probabilityToApplyStatus: Int

  def applyStatus(p: Pokemon): Pokemon

import AdditionalEffect.*

object AllPokemonStatus:
  class HealthyStatus extends PokemonStatus

  class BurnStatus extends PokemonStatusWithEffect with DealDamage with ChangeAtkEffect :
    override def atkToChange: Int = 10

    override def quantityOfDamage: Int = 30

    override def probabilityToApplyStatus: Int = 30

    override def applyStatus(pokemon: Pokemon): Pokemon = if (randomDice(probabilityToApplyStatus)) {
      changeStatsEffect(pokemon, atkToChange) withStatus this
    } else {
      pokemon
    }

  class ParalyzeStatus extends PokemonStatusWithEffect with SkipTurn with ChangeSpeedEffect :
    override def speedToChange: Int = 10
    override def probabilityToApplyStatus: Int = 30
    override def probabilityToApplySkipTurn: Int = 30

    override def applyStatus(pokemon: Pokemon): Pokemon = if (randomDice(probabilityToApplyStatus)) {
      changeStatsEffect(pokemon, speedToChange) withStatus this
    } else {
      pokemon
    }
