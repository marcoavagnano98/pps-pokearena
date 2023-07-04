package util

import scala.util.Random

object Utilities:

  extension[A](seq: Seq[A])
    def pop: Option[(A, Seq[A])] = seq match
      case h :: t => Some(h, t)
      case _ => None

    def updatedHead(elem: A) : Seq[A] = seq match
      case h :: t => Seq(elem, h) concat t
      case _ => elem :: Nil


  extension (r: Random)
    def dice(probability: Int): Boolean = r.between(1, 101) <= probability
