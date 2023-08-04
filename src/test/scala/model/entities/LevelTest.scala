package model.entities
import model.entities.World.Position
import model.entities.pokemon.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers

class LevelTest extends AnyFlatSpec with Matchers:
  private val level = Level(currentLevel = 1, maxLevel = 4, bstRange = (100, 200))
  private val gridDimension = 10
  private val numberOfElementToGenerate = 3

  it should "generate a level with correct specifics" in {
    level.idLevel should include("map")
    level.gridDimension should be(gridDimension)
    level.opponents should have size numberOfElementToGenerate
    level.items should have size numberOfElementToGenerate
    level.door.state should be(DoorState.Close)
  }

  it should "remove an item from the level" in {
    val itemToRemove = level.items.head
    level.removeItem(itemToRemove)
    level.items should not contain itemToRemove
  }

  it should "remove an opponent from the level" in {
    val opponentToRemove = level.opponents.head
    level.removeOpponent(opponentToRemove)
    level.opponents should not contain opponentToRemove
  }

  it should "update the door state correctly" in {
    level.door = Door(DoorState.Close, Position(5, 9))
    val opened = level.door.updateDoor("door_open", DoorState.Open)
    level.door should not be opened
    level.door.state should be(DoorState.Close)
    opened.state should be(DoorState.Open)
  }

  it should "generate the boss with Pokemon within the specified range" in {
    val bst = (200, 300)
    val lvl = Level(currentLevel = 1, maxLevel = 1, bst, numberOfTrainersToGenerate = 10)
    lvl.opponents should have size 1
    val boss = lvl.opponents.collectFirst { case t: Trainer if t.id == "boss" => t }
    boss shouldBe defined

    boss.get.pokemonTeam.foreach(p =>
      p.bst should be >= bst._1
      p.bst should be <= bst._2)
  }