package model.entities.generator

import model.entities.generator.PokemonGenerator
import model.entities.pokemon.{ElementType, Pokemon}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class PokemonGeneratorTest extends AnyFlatSpec with should.Matchers:

  "A PokemonGenerator" should "create a seq of 4 pokemon" in{
    assertResult(4)(PokemonGenerator(4).length)
  }

  "A PokemonGenerator with a specified range" should "return a seq of pokemon with a bast only in that range" in{
    val pokemonGenerated = PokemonGenerator.getPokemonByBstRange((100,200),4)
    pokemonGenerated.forall(p => p.bst >=100 && p.bst <= 200) shouldBe true
  }

  "A PokemonGenerator" should "return Bulbasaur with specified id 1" in{
    val bulbasaur = Pokemon("1","Bulbasaur",90,49,49,45,List(),ElementType.Grass)
    PokemonGenerator.getPokemonByIdWithMoves("1").get shouldBe bulbasaur
  }