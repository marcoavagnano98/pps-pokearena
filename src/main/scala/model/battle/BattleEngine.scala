package model.battle

import model.entities.generator.PokemonGenerator
import model.entities.{Item, Potion, Trainer}
import model.entities.pokemon.ElementType.Fire
import model.entities.pokemon.{ComparatorTypeElement, Move, Pokemon, StatusEffects}

import scala.Tuple2
import scala.annotation.tailrec
import scala.language.postfixOps
import util.Utilities.{toSeq, toPair}


/** Represent the engine of [[Pokemon]] battle */
object BattleEngine:

  import TurnStatus.*
  import TrainerChoice.*



  given Ordering[Turn] = Ordering.by[Turn, (Int, Int)](t => (t.trainerChoice.priority, t.pokemon.speed)).reverse

  /**
   *
   * @param t pair of turn
   * @return sequence of updated and ordered turns
   */
  def apply(t: (Turn, Turn)): Seq[Turn] =
    for
      turn <- roundLoop(turnOrder(t)).toSeq
    yield turn withDamageStatusApplied

  /**
   *
   * @param t pair of turn
   * @return a Turn pair which decides who takes the turn first
   */
  def turnOrder(t: (Turn, Turn)): (Turn, Turn) = t.toSeq.sorted.toPair.get


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
          pair._1.turnStatus match
            case Alive =>
              pair._1.trainerChoice match
                case Attack(move) => _loop(turnAfterAttack(pair._1 withTurnPerformed, pair._2, move) swap, nTurn - 1)
                case UseBag(item) => _loop((turnAfterHeal(pair._1, item) withTurnPerformed, pair._2) swap, nTurn - 1)
            case _ => pair swap
        case _ => turnPair

    _loop(turnPair, 2)

  /**
   *
   * @param t1   the attacking pokemon's turn
   * @param t2   the defending pokemon's turn
   * @param move move used by attacker
   * @return turn of attacker with move updated and turn of defender with pokemon demaged
   */
  def turnAfterAttack(t1: Turn, t2: Turn, move: Move): (Turn, Turn) =
    val stab = ComparatorTypeElement(move.elementType, t2.pokemon.elementType)

    val computeTotalDamage: (Int, Int, Int) => Int =
      (power, attack, defense) => ((2 + (((42 * power * attack) / defense) / 50)) * stab).toInt

    val pokemonDamaged = move.applyStatus(
      t2.pokemon.withHp(
        t2.pokemon.hp - computeTotalDamage(move.damage, t1.pokemon.attack, t2.pokemon.defense)))

    val pokemonWithMoveUpdated = t1.pokemon withUpdateMove(move.withReducePowerPoint(), t1.pokemon.moves.indexOf(move))
    (t1 withPokemonUpdate pokemonWithMoveUpdated, t2 withPokemonUpdate pokemonDamaged)

  /**
   *
   * @param b1   turn of the pokemon that needs to be healed
   * @param item healing tool
   * @return turn updated after pokemon healing
   */
  def turnAfterHeal(b1: Turn, item: Item): Turn =
    b1.withPokemonUpdate(item.use(b1.pokemon))