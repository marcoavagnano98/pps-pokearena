package model.battle

import model.entities.Trainer
import model.entities.pokemon.AdditionalEffects.{GainDamage, SkipTurn}
import model.entities.pokemon.ElementType.Fire
import model.entities.pokemon.{Move, ParalyzeStatus, Pokemon}

object BattleEngine:

  import BattleOption.*

  def apply(unitsInBattle: Seq[BattleUnit]): Seq[BattleUnit] =
    for
      turn <- Turn(unitsInBattle).generate
      unitAfterTurn <- performTurn(turn, turn._1.battleOption)
    yield unitAfterTurn

  def performTurn(t: (BattleUnit, BattleUnit), option: BattleOption): Option[BattleUnit] =
    option match
      case Attack(move) => withLife(attack(t, move))
      case _ => ???

  def attack(t: (BattleUnit, BattleUnit), move: Move): BattleUnit =
    val computeTotalDamage: (Int, Int, Int) => Int = (power, attack, defense) => (4 * power * (attack / defense)) / 50
    t._2.withPokemonUpdate(
      move.applyStatus(
        t._2.pokemon.withHp(
          t._2.pokemon.hp - computeTotalDamage(
            t._1.pokemon.attack,
            t._2.pokemon.defense,
            move.damage
          )))
    )

  def withLife(unit: BattleUnit): Option[BattleUnit] =
    unit.pokemon.hp match
      case value: Int if value > 0 => Some(unit)
      case _ => None

case class Turn(unitsInBattle: Seq[BattleUnit]):
  extension (seq: Seq[BattleUnit])
    def applyStatusEffect: Seq[BattleUnit] =
      seq.filter({
        case battleUnit: BattleUnit => battleUnit.pokemon.status match
          case s: SkipTurn => !s.applyStatus(battleUnit.pokemon)
          case _ => true
      }).map(battleUnit => battleUnit.pokemon.status match {
        case s: GainDamage => battleUnit withPokemonUpdate s.applyStatus(battleUnit.pokemon)
        case _ => battleUnit
      })

    def sortBySpeed: Seq[BattleUnit] =
      seq.sortWith(_.pokemon.speed > _.pokemon.speed)

  val generate: Seq[(BattleUnit, BattleUnit)] =
    for
      unitInTurn <- unitsInBattle.applyStatusEffect.sortBySpeed
      unitInBattle <- unitsInBattle if !(unitInTurn equals unitInBattle)
    yield (unitInTurn, unitInBattle)
