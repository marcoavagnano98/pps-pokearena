package prolog

import PrologEngine.PrologUtils.{*, given}

/**
 * Maintains
 */
object PrologApp:
  protected val defaultTheory: String = "prolog/range_theory.pl"
  protected var engine: PrologEngine = _

  def apply(): Unit = engine = PrologEngine(defaultTheory)

  /**
   * Generator for BST range
   */

  object BstGenerator:

    def generate(difficulty: Int, nLevels: Int): Iterator[(Int,Int)] =
      engine.setTheory(defaultTheory)
      engine.generateRange(difficulty, nLevels)