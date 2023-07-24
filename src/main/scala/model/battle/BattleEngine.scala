package model.battle

import model.entities.{Item, Potion, Trainer}
import model.entities.pokemon.ElementType.Fire
import model.entities.pokemon.{ComparatorTypeElement, Move, Pokemon, StatusEffects}

import scala.Tuple2
import scala.annotation.tailrec
import scala.language.postfixOps

/** Represent the engine of [[Pokemon]] battle */
object BattleEngine:

  import TurnEvent.*

  given Ordering[Turn] = Ordering.by[Turn, (Int, Int)](t => (t.battleTurnEvent.priority, t.pokemon.speed)).reverse

  /**
   *
   * @param t pair of turn
   * @return sequence of updated and ordered turns
   */
  def apply(t: (Turn, Turn)): Seq[Turn] =
    for
      battlePair <- Seq(roundLoop(turnOrder(t)))
      seqWithDamageStatusApplied <- Seq(battlePair._1 withDamageStatusApplied, battlePair._2 withDamageStatusApplied)
    yield seqWithDamageStatusApplied

  /**
   *
   * @param t pair of turn
   * @return a Turn pair which decides who takes the turn first
   */
  def turnOrder(t: (Turn, Turn)): (Turn, Turn) =
    Seq(t._1, t._2).sorted match
      case Seq(t1, t2) => (t1, t2)

  /**
   *
   * @param turnPair a data structure who will update during the battle
   * @return updated pair after the battle
   */
  def roundLoop(turnPair: (Turn, Turn)): (Turn, Turn) =
    @tailrec
    def _loop(turnPair: (Turn, Turn), nTurn: Int): (Turn, Turn) =
      (turnPair._1.checkSkipStatus, turnPair._2) match
        case pair if nTurn > 0 =>
          pair._1.battleTurnEvent match
            case Attack(move) => _loop((pair._1, turnAfterAttack(pair._1, pair._2, move)) swap, nTurn - 1)
            case UseBag(item) => _loop((turnAfterHeal(pair._1, item), pair._2) swap, nTurn - 1)
            case _ => _loop(pair.swap, nTurn - 1)
        case _ => turnPair
    _loop(turnPair, 2)

  /**
   *
   * @param b1 the attacking pokemon's turn
   * @param b2 the defending pokemon's turn
   * @param move move used by attacker
   * @return updated turn of defender
   */
  def turnAfterAttack(b1: Turn, b2: Turn, move: Move): Turn =
    val stab = ComparatorTypeElement(move.elementType, b2.pokemon.elementType)
    val computeTotalDamage: (Int, Int, Int) => Int =
      (power, attack, defense) => ((2 + (((42 * power * attack) / defense) / 50)) * stab).toInt

    val totDamage = computeTotalDamage(move.damage, b1.pokemon.attack, b2.pokemon.defense)
    b2.withPokemonUpdate(
      move.applyStatus(
        b2.pokemon.withHp(
          b2.pokemon.hp - totDamage)))

  /**
   *
   * @param b1 turn of the pokemon that needs to be healed
   * @param item healing tool
   * @return turn updated after pokemon healing
   */
  def turnAfterHeal(b1: Turn, item: Item): Turn =
    b1.withPokemonUpdate(item.use(b1.pokemon))
