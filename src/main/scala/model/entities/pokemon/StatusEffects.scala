package model.entities.pokemon

import scala.util.Random
import util.Utilities.dice

object StatusEffects:
  trait StatusEffect:
    /**
     * The type that the method [[applyEffect]] returns, based on which effect is applied.
     */
    type Result

    /**
     * @param pokemon The [[Pokemon]] to which the [[StatusEffect]] is applied.
     * @return The [[Result]] based on the [[StatusEffect]] it was applied to the [[Pokemon]]
     */
    def applyEffect(pokemon: Pokemon): Result

  /**
   * [[SkipTurnEffect]] is a [[StatusEffect]] that skip the turn of Pokemon while its in a fight
   */
  trait SkipTurnEffect extends StatusEffect :
    override type Result = Boolean

    /**
     * @return the probability to apply skipTurn Status
     */
    def probabilityToApplySkipTurn: Int

    override def applyEffect(pokemon: Pokemon): Result = Random.dice(probabilityToApplySkipTurn)

  /**
   * [[DealDamageEffect]] is a [[StatusEffect]] that deal damage to the Pokemon every turn while its in a fight
   */
  trait DealDamageEffect extends StatusEffect :
    /**
     * @return the damage over time to apply
     */
    def damageOverTime: Int

    override type Result = Pokemon

    override def applyEffect(pokemon: Pokemon): Result = pokemon withHp (pokemon.hp - damageOverTime)

  /**
   * Change a generic statistic of the Pokemon
   */
  trait ChangeStatsEffect:
    /**
     * @param pokemon The [[Pokemon]] to which the [[ChangeStatsEffect]] is applied.
     * @return The new [[Pokemon]] with the statistic updated
     */
    def applyChangeStat(pokemon: Pokemon): Pokemon

  /**
   * Change the hp statistic of the Pokemon
   */
  trait ChangeHpEffect extends ChangeStatsEffect :
    /**
     * @return the amount of hp to change to the hp statistic
     */
    def hpToChange: Int

    override def applyChangeStat(pokemon: Pokemon): Pokemon =
      pokemon withHp pokemon.hp + hpToChange

  /**
   * Change the attack statistic of the Pokemon
   */
  trait ChangeAtkEffect extends ChangeStatsEffect :
    /**
     * @return the amount of attack to change to the attack statistic
     */
    def atkToChange: Int

    override def applyChangeStat(pokemon: Pokemon): Pokemon =
      pokemon withAtk pokemon.attack + atkToChange

  /**
   * Change the speed statistic of the Pokemon
   */
  trait ChangeSpeedEffect extends ChangeStatsEffect :
    /**
     * @return the amount of speed to change to the speed statistic
     */
    def speedToChange: Int

    override def applyChangeStat(pokemon: Pokemon): Pokemon =
      pokemon withSpeed pokemon.speed + speedToChange