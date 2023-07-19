package model.battle

import scala.collection.immutable.{AbstractSeq, LinearSeq}
import scala.language.postfixOps

trait Pair[A]:

  def first: A

  def second: A

  def withFirstUpdated(first: A): Pair[A]

  def withSecondUpdated(second: A): Pair[A]

  def switched: Pair[A]

  def toSeq: Seq[A] = Seq(first, second)

object BattlePair:

  def apply(first: BattleUnit, second: BattleUnit): Pair[BattleUnit] = BattlePairImpl(first, second)

  def apply(seq: Seq[BattleUnit]): Pair[BattleUnit] =
    seq match
      case Seq(first, second) => BattlePair(first, second)
      case _ => throw IllegalArgumentException("Seq must has exactly two arguments of BattleUnit type")

  private case class BattlePairImpl(override val first: BattleUnit, override val second: BattleUnit) extends Pair[BattleUnit]:
    
    override def withFirstUpdated(first: BattleUnit): Pair[BattleUnit] = copy(first = first)

    override def withSecondUpdated(second: BattleUnit): Pair[BattleUnit] = copy(second = second)

    override def switched: Pair[BattleUnit] = copy(first = second, second = first)