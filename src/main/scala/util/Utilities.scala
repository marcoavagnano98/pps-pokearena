package util

import model.entities.World.Position
import model.entities.Level

import scala.annotation.tailrec
import scala.collection.immutable.{AbstractSeq, LinearSeq}
import scala.util.Random

object Utilities:

  extension (r: Random)
    def dice(probability: Int): Boolean = r.between(1, 101) <= probability

  extension[A](t: (A, A))
    def toSeq: Seq[A] = Seq(t._1, t._2)
    
  extension[A](seq: Seq[A])
    def toPair: Option[(A, A)] =
      seq match
        case Seq(e1, e2) => Some((e1, e2))
        case _ => None
      