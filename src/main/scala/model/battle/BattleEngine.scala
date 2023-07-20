package model.battle

import model.entities.{Item, Potion, Trainer}
import model.entities.pokemon.ElementType.Fire
import model.entities.pokemon.{ComparatorTypeElement, Move, Pokemon, StatusEffects}

import scala.Tuple2
import scala.annotation.tailrec
import scala.language.postfixOps

object BattleEngine:

  import BattleTurnEvent.*

  given Ordering[Turn] = Ordering.by[Turn, Int](_.pokemon.speed).reverse

  def apply(t: (Turn, Turn)): Seq[Turn] =
    for
      battlePair <- Seq(turnLoop(turnOrder(t)))
      seqWithDamageStatusApplied <- Seq(battlePair._1 withDamageStatusApplied, battlePair._2 withDamageStatusApplied)
    yield seqWithDamageStatusApplied

  def turnOrder(t: (Turn, Turn)): (Turn, Turn) =
    Seq(t._1, t._2).sorted match
      case Seq(t1, t2) => (t1, t2)

  def turnLoop(battlePair: (Turn, Turn)): (Turn, Turn) =
    @tailrec
    def _loop(turnPair: (Turn, Turn), turnLife: Int): (Turn, Turn) =
      (turnPair._1.checkSkipStatus, turnPair._2) match
        case pair if turnLife > 0 =>
          pair._1.battleTurnEvent match
            case Attack(move) => _loop((pair._1, unitAfterAttack(pair._1, pair._2, move)).swap, turnLife - 1)
            case Bag(item) => _loop((unitAfterHeal(pair._1, item), pair._2).swap, turnLife - 1)
            case _ => _loop(pair.swap, turnLife - 1)
        case _ => turnPair
    _loop(battlePair, 2)

  def unitAfterAttack(b1: Turn, b2: Turn, move: Move): Turn =
    val stab = ComparatorTypeElement(move.elementType, b2.pokemon.elementType)
    val computeTotalDamage: (Int, Int, Int) => Int =
      (power, attack, defense) => ((2 + (((42 * power * attack) / defense) / 50)) * stab).toInt

    val totDamage = computeTotalDamage(move.damage, b1.pokemon.attack, b2.pokemon.defense)
    b2.withPokemonUpdate(
      move.applyStatus(
        b2.pokemon.withHp(
          b2.pokemon.hp - totDamage)))

  def unitAfterHeal(b1: Turn, item: Item): Turn =
    b1.withPokemonUpdate(item.use(b1.pokemon))