package model.pokemon

import model.entities.Potion
import model.entities.World.Position
import model.entities.pokemon.AllPokemonStatus.BurnStatus
import model.entities.pokemon.{ElementType, Move, Pokemon}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class PokemonStatusTest extends AnyFlatSpec with should.Matchers:

  private var bulbasaur = Pokemon("1","Bulbasaur",50,49,49,45,List(),ElementType.Grass)
  private var venusaur = Pokemon("3","Venusaur",50,49,49,45,List(),ElementType.Grass)

  private val ember = Move(10,10,"ember",ElementType.Fire,Some(BurnStatus()))

  "Ember move" should "change the status of pokemon from Healthy to BurnStatus" in {
    bulbasaur = ember applyStatus bulbasaur
    assertResult(BurnStatus())(bulbasaur.status)
  }

  "Ember move" should "decrease the atk stat of pokemon by 10 with 30% of probability" in {
    var passed = false
    for i <- 1 to 100 do
      venusaur = ember applyStatus venusaur
      if 39 == venusaur.attack then
        passed = true
    assertResult(true)(passed)
  }

