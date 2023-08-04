package model.pokemon

import model.entities.Potion
import model.entities.World.Position
import model.entities.generator.PokemonGenerator
import model.entities.pokemon.AllPokemonStatus.{BurnStatus, FreezeStatus, ParalyzeStatus}
import model.entities.pokemon.{ElementType, Move, Pokemon}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class PokemonStatusTest extends AnyFlatSpec with should.Matchers:

  private var bulbasaur = Pokemon("1","Bulbasaur",50,49,49,45,List(),ElementType.Grass)
  private var venusaur = Pokemon("3","Venusaur",50,49,49,45,List(),ElementType.Grass)

  private val ember = Move(10,10,"ember",ElementType.Fire,Some(BurnStatus()))
  private val icePunch = Move(10,10,"icePunch",ElementType.Ice,Some(FreezeStatus()))
  private val thunder = Move(10,10,"thunder",ElementType.Electric,Some(ParalyzeStatus()))

  "A move with BurnStatus" should "change the status of pokemon from Healthy to BurnStatus" in {
    for i <- 1 to 5000 do
      bulbasaur = ember applyStatus bulbasaur
    assertResult(BurnStatus())(bulbasaur.status)
  }

  "A move with a status attack modifier" should "decrease the atk stat of pokemon by 10 with 30% of probability" in {
    for i <- 1 to 5000 do
      venusaur = ember applyStatus venusaur
    assertResult(39)(venusaur.attack)
  }

  "A move with FreezeStatus" should "change the status of pokemon from Healthy to Freeze" in {
    for i <- 1 to 5000 do
      bulbasaur = icePunch applyStatus bulbasaur
    assertResult(FreezeStatus())(bulbasaur.status)
  }


  "A move with ParalyzeStatus" should "change the status of pokemon from Healthy to Paralyze" in {
    for i <- 1 to 5000 do
      bulbasaur = thunder applyStatus bulbasaur
    assertResult(ParalyzeStatus())(bulbasaur.status)
  }


