package model

import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import model.entities.pokemon.{Pokemon, PokemonFactory}
import model.entities.pokemon.ElementType

class PokemonFactoryTest extends AnyFlatSpec with should.Matchers:

  private val bulbasaur = Pokemon("1","Bulbasaur",45,49,49,45,List(),ElementType.Grass)
  "A PokemonFactory" should "create a seq of 4 pokemon" in{
    assertResult(4)(PokemonFactory(4).length)
  }

  "A PokemonFactory" should "return Bulbasaur with specified id 1" in{
    bulbasaur shouldBe PokemonFactory.getPokemonById("1").get
  }
