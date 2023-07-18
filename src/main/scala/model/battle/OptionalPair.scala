package model.battle

import scala.collection.immutable.{AbstractSeq, LinearSeq}
import scala.language.postfixOps

trait OptionalPair[A]:

  def first: Option[A]

  def second: Option[A]

  def withFirstUpdated(first: A): OptionalPair[A]

  def withSecondUpdated(second: A): OptionalPair[A]

  def switched: OptionalPair[A]

  def toSeq: Seq[Option[A]] = Seq(first, second)

object BattlePair:

  def apply(first: BattleUnit, second: BattleUnit): OptionalPair[BattleUnit] = BattlePairImpl(first withLife, second withLife)

  def apply(seq: Seq[BattleUnit]): OptionalPair[BattleUnit] =
    seq match
      case Seq(first, second) => BattlePair(first, second)
      case _ => throw IllegalArgumentException("Seq must has exactly two arguments of BattleUnit type")

  private case class BattlePairImpl(override val first: Option[BattleUnit], override val second: Option[BattleUnit]) extends OptionalPair[BattleUnit]:
    override def withFirstUpdated(first: BattleUnit): OptionalPair[BattleUnit] = copy(first = first withLife)

    override def withSecondUpdated(second: BattleUnit): OptionalPair[BattleUnit] = copy(second = second withLife)

    override def switched: OptionalPair[BattleUnit] = copy(first = second, second = first)