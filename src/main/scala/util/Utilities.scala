package util

import scala.util.Random

object Utilities:

  extension (r: Random)
    def dice(probability: Int): Boolean = (r.between(1, 101)) <= probability

