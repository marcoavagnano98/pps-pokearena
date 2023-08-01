package prolog

import PrologEngine.PrologUtils.{*, given}
import model.entities.pokemon.PokemonFactory
object PrologApp:

  object BstGenerator:
    lazy val engine: PrologEngine = PrologEngine("prolog/range_theory.pl")

    def generate(difficulty: Int, nLevels: Int): Iterator[(Int,Int)] =
      engine.generateRange(difficulty, nLevels)


@main
def main(): Unit =
  import PrologApp.BstGenerator

  val it = BstGenerator.generate(3,50)
  PokemonFactory.getPokemonByBstRange(600,720)