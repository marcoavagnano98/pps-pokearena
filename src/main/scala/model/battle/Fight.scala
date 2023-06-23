package model.battle

import model.entities.pokemon.AdditionalEffects.{GainDamage, SkipTurn}
import model.entities.pokemon.ElementType.Fire
import model.entities.pokemon.{ParalyzeStatus, Pokemon, Move}

/**
 * [[Fight]] models clash between two [[Pokemon]]
 */
trait Fight:
  /**
   *
   * @return player's [[Pokemon]]
   */
  def playerPokemon: Pokemon

  /**
   *
   * @return opponent's [[Pokemon]]
   */
  def opponentPokemon: Pokemon

  /**
   *
   * @return defeated [[Pokemon]]
   */
  def endFight: Boolean

object Fight:
  def apply(p1: Pokemon, p2: Pokemon, moves: Map[String, Move]): Seq[Pokemon] =
    for
      turn <- FightTurn(Seq(p1, p2)).generate
      alive <- withLife(attack(turn, moves(turn._1.id)))
    yield alive


  def attack(t: (Pokemon, Pokemon), move: Move): Pokemon =
      val computeTotalDamage: (Int, Int, Int) => Int = (power, attack, defense) => (42 * power * (attack / defense)) / 50
      move.applyStatus(
        t._2.withHp(
        t._2.hp - computeTotalDamage(
          t._1.attack,
          t._2.defense,
          move.damage
        )))

  def withLife(pokemon: Pokemon): Option[Pokemon] =
    pokemon.hp match
    case value: Int if value > 0 => Some(pokemon)
    case _ => None



  case class FightTurn(pokemonInBattle: Seq[Pokemon]):
    extension (seq: Seq[Pokemon])
      def applyStatusEffect: Seq[Pokemon] =
        seq.filter({
          case pokemon: Pokemon => pokemon.status match
            case s: SkipTurn => !s.applyStatus(pokemon)
            case _ => true
        }).map(p => p.status match {
          case s: GainDamage => s.applyStatus(p)
          case _ => p
        })

      def bySpeed: Seq[Pokemon] =
        seq.sortWith(_.speed > _.speed)

    val generate: Seq[(Pokemon, Pokemon)] =
      for
        p <- pokemonInBattle.applyStatusEffect.bySpeed
        p2 <- pokemonInBattle if !p.equals(p2)
      yield (p, p2)
