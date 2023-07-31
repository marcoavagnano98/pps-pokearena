package model

import model.entities.pokemon.{ElementType, Pokemon}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class PokemonTest extends AnyFlatSpec with should.Matchers:
  private val bulbasaur = Pokemon("1","Bulbasaur",45,49,49,45,List(),ElementType.Grass)
  
  "A Pokemon" should "not have an hp update below 0" in{
    assertResult(0)((bulbasaur withHp -1).hp)
  }

  "A Pokemon" should "not have an hp update that exceed his max life" in{
    assertResult(45)((bulbasaur withHp 100).hp)
  }
