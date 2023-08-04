package model.entities
import model.entities.World.Position
import model.entities.*
import model.entities.generator.PokemonGenerator
import model.entities.pokemon.{ElementType, Pokemon}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers
import pokearena.PokeArena

class WorldTest extends AnyFlatSpec with BeforeAndAfter with Matchers:
  private var world: World = _
  private val pokemonTeam: Seq[Pokemon] = Seq(Pokemon("3", "Charmander", 50, 52, 43, 65, List(), ElementType.Fire),
    Pokemon("4", "Squirtle", 50, 48, 65, 43, List(), ElementType.Water))
  private var item: Item = _
  private var opponent: Trainer = _
  private var closedDoor: Door = _

  before {
    PokeArena.initPrologEngine()
    world = World()
    world.createLevel(pokemonTeam)
    item = world.level.items.head
    opponent = world.level.opponents.head
    closedDoor = world.level.door
  }

  it should "create the current Level and provide the Player with the Pokemon team" in {
    world.level should not be null
    world.player.pokemonTeam should be(pokemonTeam)
  }

  it should "return the VisibleEntity with which a collision has occurred" in {
    world.checkCollision should not be Some(item)
    world.checkCollision should not be Some(opponent)
    world.checkCollision should not be Some(closedDoor)
    world.player = world.player.withPosition(item.position)
    world.checkCollision should be(Some(item))
    world.player = world.player.withPosition(opponent.position)
    world.checkCollision should be(Some(opponent))
    world.player = world.player.withPosition(closedDoor.position)
    world.checkCollision should be(Some(closedDoor))
  }

  it should "add the item to the player's Bag and remove it from the level" in {
    world.player.bag.items should have size 0
    world.itemCollision(item)
    world.player.bag.items should have size 1
    world.player.bag.items should contain(item)
    world.level.items should not contain item
  }

  it should "not update the level colliding with a closed door" in {
    world.player = world.player.withPosition(Position(6,6))
    world.doorCollision(closedDoor)
    world.currentLevel should be(1)
    world.level.door.state should be(DoorState.Close)
    world.player.position should be (Position(6,6))
  }

  it should "update the level and player when colliding with an open door" in {
    world.doorCollision(closedDoor)
    world.level.door.state should not be DoorState.Open
    world.currentLevel should be(1)
    world.updateDoor
    world.level.door.state should be(DoorState.Open)
    world.doorCollision(world.level.door)
    world.currentLevel should be(2)
    world.player.position should be(Position(0, 0))
  }

  it should "update the doorState when all trainers are defeated" in {
    world.level.door = closedDoor
    world.level.opponents.foreach(world.removeTrainer)
    world.updateDoor
    world.level.door.state should be(DoorState.Open)
  }

  it should "return Lose when all levels are not completed" in {
    world.isGameWon should be(GameStatus.Lose)
  }

  it should "return Win when all levels are completed and the Boss defeated" in {
    for i <- world.currentLevel to 4 do
      world.updateDoor
      world.doorCollision(world.level.door)

    world.isGameWon should be(GameStatus.Win)
  }
