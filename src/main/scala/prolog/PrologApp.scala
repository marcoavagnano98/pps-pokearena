package prolog

import PrologEngine.PrologUtils.{*, given}

object PrologApp:

  object BstGenerator:
    val engine: PrologEngine = PrologEngine("prolog/range_theory.pl")

    def generate(difficulty: Int, nLevels: Int): Iterator[(Int,Int)] =
      engine.generateRange(difficulty, nLevels)