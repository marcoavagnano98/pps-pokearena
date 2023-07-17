package util

import scala.util.Random

object Utilities:

  extension[A](seq: Seq[A])


    def pop: (A, Seq[A]) = (seq.head, seq.tail)

    def push(elem: A) : List[A] = elem :: seq.toList

    def swap(startIndex: Int, finalIndex: Int): Seq[A] =
      seq updated(startIndex,  seq(finalIndex)) updated(finalIndex, seq(startIndex))


  extension (r: Random)
    def dice(probability: Int): Boolean = r.between(1, 101) <= probability