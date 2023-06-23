package util

import scala.util.Random

object Utilities:


  extension (r: Random)
    def dice(probability: Int): Boolean = (r.between(1, 101)) <= probability 
    
//  private val random = Random()
//  def randomDice(probability: Int): Boolean = (random.between(1, 101)) <= probability
