package model.entities
import model.entities.World.Position
import model.entities.pokemon.{ElementType, Pokemon}
import model.entities.{ItemFactory, ItemType, Potion, SuperPotion, Item, World}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers

class DoorTest extends AnyFlatSpec with Matchers:
  private val initialState = DoorState.Close
  private val position = Position(5, 10)
  private var door = Door(initialState, position)
  private val world = World()

  it should "checks that the newly created door meets the specifics" in {
    door.id should be("door_close")
    door.state should be(initialState)
    door.position should be(position)
  }

  it should "update the door state and id correctly" in {
    door = door.updateDoor("door_open", DoorState.Open)
    door.id should be("door_open")
    door.state should be(DoorState.Open)
  }

  it should "open the door when the all the opponents are defeated" in {
    world.createLevel(Seq.empty)
    world.level.door.id should be("door_close")
    world.level.door.state should be(DoorState.Close)
    world.updateDoor
    world.level.door.id should be("door_open")
    world.level.door.state should be(DoorState.Open)
  }


