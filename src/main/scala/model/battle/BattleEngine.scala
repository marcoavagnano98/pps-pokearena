package model.battle

import model.battle.{BattlePair, OptionalPair}
import model.entities.Trainer

import model.entities.pokemon.ElementType.Fire
import model.entities.pokemon.{Move, Pokemon}

import scala.Tuple2
import scala.annotation.tailrec
import scala.language.postfixOps

object BattleEngine:
  import BattleOption.*

  given Ordering[BattleUnit] = Ordering.by[BattleUnit, Int](_.pokemon.speed).reverse

  given Conversion [(BattleUnit,BattleUnit), OptionalPair[BattleUnit]] with
    def apply(t: (BattleUnit, BattleUnit)): OptionalPair[BattleUnit] =
      BattlePair(Seq(t._1, t._2).sorted)

  def apply(t:(BattleUnit, BattleUnit)): Seq[BattleUnit] =
    for
      battleUnit <- performTurn(t).toSeq
      battleUnitAlive <- battleUnit
      battleUnitUpdated <-{println(battleUnitAlive.pokemon);  battleUnitAlive.withDamageStatusApplied.withLife }
    yield battleUnitUpdated

  def performTurn(battlePair: OptionalPair[BattleUnit]): OptionalPair[BattleUnit] =
    @tailrec
    def _loop(battlePair: OptionalPair[BattleUnit], turnLife: Int): OptionalPair[BattleUnit] =
      (battlePair.first, battlePair.second) match
        case (Some(firstUnit), Some(secondUnit)) if turnLife > 0 =>
          firstUnit.battleOption match
            case Attack(move) if !(firstUnit skipEffect) => _loop(battlePair withSecondUpdated unitAfterAttack(firstUnit, secondUnit, move) switched, turnLife - 1)
            case _ => _loop(battlePair switched, turnLife - 1)
        case _ => battlePair
    _loop(battlePair, battlePair.toSeq.size)

  def unitAfterAttack(b1: BattleUnit, b2: BattleUnit, move: Move): BattleUnit =
    
    val computeTotalDamage: (Int, Int, Int) => Int = (power, attack, defense) => (4 * power * (attack / defense)) / 50

    b2.withPokemonUpdate(
      move.applyStatus(
        b2.pokemon.withHp(
          b2.pokemon.hp - computeTotalDamage(
            move.damage,
            b1.pokemon.attack,
            b2.pokemon.defense
          ))))

  def heal: BattleUnit = ???

