package model.battle

import model.entities.pokemon.AdditionalEffects.{GainDamage, SkipTurn}
import model.entities.pokemon.{Move, ParalyzeStatus, Pokemon, PokemonStatus, PokemonStatusWithEffect}

import scala.collection.IterableFactory
import scala.language.postfixOps

object BattleEngine:
  def applyDamage(t:FightTurn, move: Move):Unit=
    val computeTotalDamage: (Int ,Int, Int) => Int = (power, attack, defense) => (42 * power * (attack / defense)) / 50
    /*
    FightTurn(p1, p2)
    yield (p1,p2.applyLifeChange(
    p2.hp - computeTotalDamage(
      p1.attack,
      p2.defense,
      move.power)))*/

  //def applyStatusEffect(p: Pokemon): Pokemon =
   // p.status.applyDamage(p)
