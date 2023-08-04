package model.entities
import model.entities.World.Position
import model.entities.pokemon.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers
import pokearena.PokeArena

class BagTest extends AnyFlatSpec with BeforeAndAfter with Matchers:
  private val bag = Bag()
  private val potion = ItemFactory(ItemType.Potion)
  private val superPotion = ItemFactory(ItemType.SuperPotion)
  private var world: World = _

  before {
    PokeArena.initPrologEngine()
    world = World()
  }

  it should "add an item to the bag" in {
    bag.addItem(potion)
    bag.items should contain(potion)
  }

  it should "remove an item from the bag" in {
    bag.addItem(potion)
    bag.removeItem(potion)
    bag.items should not contain potion
  }

  it should "retrieve an item from the bag by index" in {
    bag.addItem(potion)
    bag.addItem(superPotion)
    val retrievedPotion = bag.getItem(0)
    val retrievedSuperPotion = bag.getItem(1)
    retrievedPotion should be(potion)
    retrievedSuperPotion should be(superPotion)
  }

  it should "return an empty list of items if the bag doesn't contain items" in {
    removeAllItems()
    bag.items shouldBe empty
  }

  it should "return the correct number of items in the bag" in {
    bag.addItem(potion)
    bag.addItem(superPotion)
    bag.items should have size 2
  }

  it should "add an item to the bag when the player collide with it" in {
    world.createLevel(Seq.empty)
    world.player.bag.items should have size 0
    world.itemCollision(superPotion)
    world.player.bag.items should have size 1
    world.player.bag.items should contain(superPotion)
    world.player.bag.items should not contain potion
  }

  private def removeAllItems(): Unit =
    bag.items.foreach(item => bag.removeItem(item))