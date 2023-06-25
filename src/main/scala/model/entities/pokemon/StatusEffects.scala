package model.entities.pokemon

import scala.util.Random
import util.Utilities.dice

object StatusEffects:
  trait StatusEffect:
    type Result
    def applyEffect(pokemon: Pokemon): Result

  trait SkipTurnEffect extends StatusEffect :
    override type Result = Boolean
    def probabilityToApplySkipTurn: Int
    override def applyEffect(pokemon: Pokemon): Result = Random.dice(probabilityToApplySkipTurn)

  trait DealDamageEffect extends StatusEffect :
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