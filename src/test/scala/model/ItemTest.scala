package model

import model.entities.pokemon.Pokemon
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import model.entities.Potion
import model.entities.pokemon.ElementType
import model.entities.World.Position

class ItemTest extends AnyFlatSpec with should.Matchers:

  private var bulbasaur = Pokemon("1","Bulbasaur",50,49,49,45,List(),ElementType.Grass)
  private val potion = Potion(position = Position(12,41))

  "A potion" should "increase the amount of hp of pokemon by 50" in {
    bulbasaur = bulbasaur withHp 0
    bulbasaur = potion use bulbasaur
    assertResult(50)(bulbasaur.hp)
  }

