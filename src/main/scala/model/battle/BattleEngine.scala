package model.battle

import model.entities.{Item, Potion, Trainer}
import model.entities.pokemon.ElementType.Fire
import model.entities.pokemon.{ComparatorTypeElement, Move, Pokemon, StatusEffects}

import scala.Tuple2
import scala.annotation.tailrec
import scala.language.postfixOps

object BattleEngine:

  import BattleTurnEvent.*

  given Ordering[BattleUnit] = Ordering.by[BattleUnit, Int](_.pokemon.speed).reverse

  def apply(t: (BattleUnit, BattleUnit)): Seq[BattleUnit] =
    for
      battlePair <- Seq(turnLoop(turnOrder(t)))
      seqWithDamageStatusApplied <- Seq(battlePair._1 withDamageStatusApplied, battlePair._2 withDamageStatusApplied)
    yield seqWithDamageStatusApplied

  def turnOrder(t: (BattleUnit, BattleUnit)): (BattleUnit, BattleUnit) =
    Seq(t._1, t._2).sorted match
      case Seq(t1, t2) => (t1, t2)

  def turnLoop(battlePair: (BattleUnit, BattleUnit)): (BattleUnit, BattleUnit) =
    @tailrec
    def _loop(battlePair: (BattleUnit, BattleUnit), turnLife: Int): (BattleUnit, BattleUnit) =
      (battlePair._1.checkSkipStatus, battlePair._2) match
        case pair if turnLife > 0 =>
          pair._1.battleTurnEvent match
            case Attack(move) => _loop((pair._1, unitAfterAttack(pair._1, pair._2, move)).swap, turnLife - 1)
            case Bag(item) => _loop((unitAfterHeal(pair._1, item), pair._2).swap, turnLife - 1)
            case _ => _loop(pair.swap, turnLife - 1)
        case _ => battlePair
    _loop(battlePair, 2)

  def unitAfterAttack(b1: BattleUnit, b2: BattleUnit, move: Move): BattleUnit =
    val stab = ComparatorTypeElement(move.elementType, b2.pokemon.elementType)
    val computeTotalDamage: (Int, Int, Int) => Int =
      (power, attack, defense) => ((2 + (((42 * power * attack) / defense) / 50)) * stab).toInt

    val totDamage = computeTotalDamage(move.damage, b1.pokemon.attack, b2.pokemon.defense)
    b2.withPokemonUpdate(
      move.applyStatus(
        b2.pokemon.withHp(
          b2.pokemon.hp - totDamage)))

  def unitAfterHeal(b1: BattleUnit, item: Item): BattleUnit =
    b1.withPokemonUpdate(item.use(b1.pokemon))





