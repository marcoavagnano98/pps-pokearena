package model.entities
import model.entities.World.Position
import model.entities.VisibleEntity
import model.entities.pokemon.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers

class EntityTest extends AnyFlatSpec with Matchers:
  private val pokemon = Pokemon("1", "Pikachu", 50, 40, 35, 55, List(), ElementType.Electric)
  private var player = Player(Position(2, 3), "op0", List())
  private val item: Item = ItemFactory(ItemType.Potion, Position(5,5))
  private val door = Door(DoorState.Close, Position(6, 7))
  
  it should "return the correct width for each type of entity" in {
    EntityDefaultValues.width(pokemon) should be(5)
    EntityDefaultValues.width(player) should be(10)
    EntityDefaultValues.width(item) should be(6)
    EntityDefaultValues.width(door) should be(10)
  }
  
  it should "return the correct height for each type of entity" in {
    EntityDefaultValues.height(pokemon) should be(5)
    EntityDefaultValues.height(player) should be(10)
    EntityDefaultValues.height(item) should be(6)
    EntityDefaultValues.height(door) should be(10)
  }
  
  it should "return an updated entity with the new position" in {
    player = player withPosition Position(10,3)
    player.position should not be Position(2, 3)
  }