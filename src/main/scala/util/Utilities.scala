package util

import model.entities.World.Position
import model.entities.Level

import scala.annotation.tailrec
import scala.util.Random

object Utilities:

  extension (r: Random)
    def dice(probability: Int): Boolean = r.between(1, 101) <= probability

