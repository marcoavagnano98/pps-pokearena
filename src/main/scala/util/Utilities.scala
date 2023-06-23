package util

import scala.util.Random

object Utilities:
  
  private val random = Random()
  
  extension (r: Random)
    def dice(probability: Int): Boolean = (r.between(1, 101)) <= probability
  
  def randomDice(probability: Int) = (random.between(1, 101)) <= probability
