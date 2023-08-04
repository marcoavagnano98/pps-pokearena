package view

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.badlogic.gdx.Input.Keys
import model.entities.World.Position
import model.entities.generator.PokemonGenerator
import model.entities.pokemon.{ElementType, Pokemon}
import model.entities.{Item, ItemFactory, ItemType, Potion, SuperPotion, World}
import org.scalatest.*
import org.scalatest.flatspec.*
import pokearena.PokeArena

class PlayerProcessorTest extends AnyFlatSpec with BeforeAndAfter with Matchers:
  private var world: World = _
  private var playerProcessor: PlayerProcessor = _
  private var position = Position(0,0)

  before {
    PokeArena.initPrologEngine()
    world = World()
    world.createLevel(PokemonGenerator(2))
    playerProcessor = PlayerProcessor(world)
  }

  "PlayerProcessor" should "move the player to the RIGHT when pressing the RIGHT key" in {
    world.player.position should be(position)
    playerProcessor.keyDown(Keys.RIGHT)
    world.player.position shouldBe Position(position.x + world.level.playerSpeed, position.y)
  }

  "PlayerProcessor" should "not move the player to the RIGHT if the new position is outside the bounds of the level" in {
    position = Position(9, 0)
    world.player = world.player withPosition position
    world.player.position shouldBe Position(9, 0)
    playerProcessor.keyDown(Keys.RIGHT)
    world.player.position shouldBe position
  }

  "PlayerProcessor" should "move the player to the LEFT when pressing the LEFT key" in {
    position = Position(5, 5)
    world.player = world.player withPosition position
    world.player.position shouldBe Position(5, 5)
    playerProcessor.keyDown(Keys.LEFT)
    world.player.position shouldBe Position(position.x - world.level.playerSpeed, position.y)
  }

  "PlayerProcessor" should "not move the player to the LEFT if the new position is outside the bounds of the level" in {
    position = Position(0, 5)
    world.player = world.player withPosition position
    world.player.position shouldBe Position(0, 5)
    playerProcessor.keyDown(Keys.LEFT)
    world.player.position shouldBe position
  }

  "PlayerProcessor" should "move the player UP when pressing the UP key" in {
    position = Position(0, 0)
    playerProcessor.keyDown(Keys.UP)
    world.player.position shouldBe Position(position.x, position.y + world.level.playerSpeed)
  }

  "PlayerProcessor" should "not move the player UP if the new position is outside the bounds of the level" in {
    position = Position(5, 9)
    world.player = world.player withPosition position
    world.player.position shouldBe Position(5, 9)
    playerProcessor.keyDown(Keys.UP)
    world.player.position shouldBe position
  }

  "PlayerProcessor" should "move the player DOWN when pressing the DOWN key" in {
    position = Position(3, 5)
    world.player = world.player withPosition position
    world.player.position shouldBe Position(3, 5)
    playerProcessor.keyDown(Keys.DOWN)
    world.player.position shouldBe Position(position.x, position.y - world.level.playerSpeed)
  }

  "PlayerProcessor" should "not move the player DOWN if the new position is outside the bounds of the level" in {
    position = Position(6, 0)
    world.player = world.player withPosition position
    world.player.position shouldBe Position(6, 0)
    playerProcessor.keyDown(Keys.DOWN)
    world.player.position shouldBe position
  }








