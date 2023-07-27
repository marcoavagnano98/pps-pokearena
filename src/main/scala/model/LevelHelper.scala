package model

import model.entities.Level
import model.entities.pokemon.PokemonFactory

import scala.::

enum Difficulty(val maxRange: Int):
  case Easy extends Difficulty(300)
  case Medium extends Difficulty(450)
  case Hard extends Difficulty(600)


object LevelHelper
  /*def generateLevelsByDifficulty(difficulty: Difficulty, nLevels: Int): Iterator[Level] =
    val levelsRange: Int => Seq[(Int, Int)] = (bstIncrement: Int) => 
      (0 until difficulty.maxRange by bstIncrement).foldLeft(Seq.empty)((v, s) => v :+ (s, s + bstIncrement))*/
   // levelsRange(difficulty.maxRange / nLevels)
@main
def main() =
  val bstIncrement = 100
  println((0 until 600 by bstIncrement).zip(bstIncrement to 600 by bstIncrement))
  println((0 until 600 by bstIncrement).foldLeft(Vector.empty)((v, s) => v :+ (s, s + bstIncrement)))
