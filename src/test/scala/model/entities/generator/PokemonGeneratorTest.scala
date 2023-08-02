package model.entities.generator

import model.entities.generator.PokemonGenerator
import model.entities.pokemon.{ElementType, Pokemon}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class PokemonGeneratorTest extends AnyFlatSpec with should.Matchers:

  private val bulbasaur = Pokemon("1","Bulbasaur",45,49,49,45,List(),ElementType.Grass)
  "A PokemonFactory" should "create a seq of 4 pokemon" in{
    assertResult(4)(PokemonGenerator(4).length)
  }

  "A PokemonFactory" should "return Bulbasaur with specified id 1" in{
    bulbasaur shouldBe PokemonGenerator.getPokemonById("1").get
  }
