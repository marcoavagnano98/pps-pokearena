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

    override def applyEffect(pokemon: Pokemon): Result = Random.dice(100)

  trait DealDamageEffect extends StatusEffect :
    def damageOverTime: Int

    override type Result = Pokemon

    override def applyEffect(pokemon: Pokemon): Result = pokemon withHp (pokemon.hp - damageOverTime)

  trait ChangeStatsEffect:
    def applyChangeStat(pokemon: Pokemon): Pokemon

  trait ChangeHpEffect extends ChangeStatsEffect :
    def hpToChange: Int

    override def applyChangeStat(pokemon: Pokemon): Pokemon =
      pokemon withHp pokemon.hp + hpToChange

  trait ChangeAtkEffect extends ChangeStatsEffect :
    def atkToChange: Int

    override def applyChangeStat(pokemon: Pokemon): Pokemon =
      pokemon withAtk pokemon.attack + atkToChange

  trait ChangeSpeedEffect extends ChangeStatsEffect :
    def speedToChange: Int

    override def applyChangeStat(pokemon: Pokemon): Pokemon =
      pokemon withSpeed pokemon.speed + speedToChange