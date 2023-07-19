package model.battle

import model.battle.{BattlePair, Pair}
import model.entities.{Item, Potion, Trainer}
import model.entities.pokemon.ElementType.Fire
import model.entities.pokemon.{ComparatorTypeElement, Move, Pokemon, StatusEffects}

import scala.Tuple2
import scala.annotation.tailrec
import scala.language.postfixOps

object BattleEngine:

  import BattleAction.*

  given Ordering[BattleUnit] = Ordering.by[BattleUnit, Int](_.pokemon.speed).reverse

  given Conversion[(BattleUnit, BattleUnit), Pair[BattleUnit]] with
    def apply(t: (BattleUnit, BattleUnit)): Pair[BattleUnit] =
      BattlePair(Seq(t._1, t._2).sorted)

  def apply(t: (BattleUnit, BattleUnit)): Seq[BattleUnit] =
    for
      battleUnit <- performTurn(t).toSeq
    yield battleUnit.withDamageStatusApplied

  def performTurn(battlePair: Pair[BattleUnit]): Pair[BattleUnit] =
    @tailrec
    def _loop(battlePair: Pair[BattleUnit], turnLife: Int): Pair[BattleUnit] =
      (battlePair.first, battlePair.second) match
        case (firstUnit, secondUnit) if turnLife > 0 && firstUnit.stillInBattle =>
          firstUnit.battleOption match
            case Attack(move) if !(firstUnit skipEffect) => _loop(battlePair withSecondUpdated unitAfterAttack(firstUnit, secondUnit, move) switched, turnLife - 1)
            case Attack(_) => _loop(battlePair withFirstUpdated(firstUnit withTurnSkipped) switched, turnLife - 1)
            case Bag(item) => _loop(battlePair withFirstUpdated unitAfterHeal(firstUnit, item) switched, turnLife - 1)
            case _ => _loop(battlePair switched, turnLife - 1)
        case _ => battlePair

    _loop(battlePair, battlePair.toSeq.size)

  def unitAfterAttack(b1: BattleUnit, b2: BattleUnit, move: Move): BattleUnit =
    val stab = ComparatorTypeElement(move.elementType, b2.pokemon.elementType)
    val computeTotalDamage: (Int, Int, Int) => Int = (power, attack, defense) =>
      ((2 +
        ((42 * power * (attack / defense)) / 50
        )) * stab).toInt
    val totDamage = computeTotalDamage(
      move.damage,
      b1.pokemon.attack,
      b2.pokemon.defense)
    println("Attacco di " + b1.pokemon + " " + b1.pokemon.attack )
    println("Difesa di " + b2.pokemon + " " + b2.pokemon.attack )
    println("Bonus " + stab)
    println("Danno totale " + totDamage)
    println("Danno mossa " + move.damage)
    b2.withPokemonUpdate(
      move.applyStatus(
        b2.pokemon.withHp(
          b2.pokemon.hp - totDamage)))

  def unitAfterHeal(b1: BattleUnit, item: Item): BattleUnit =
    b1.withPokemonUpdate(
      item.use(b1.pokemon)
    )





