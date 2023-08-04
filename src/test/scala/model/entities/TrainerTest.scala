package model.entities
import model.entities.World.Position
import model.entities.*
import model.entities.generator.PokemonGenerator
import model.entities.pokemon.{ElementType, Pokemon}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers

class TrainerTest extends AnyFlatSpec with Matchers:
  private val position: Position = Position(1, 2)
  private val idTrainer = "op5"
  private val pokemonTeam: Seq[Pokemon] = PokemonGenerator(2)
  private var player = Player(position, idTrainer, pokemonTeam)

  it should "create a Trainer with the correct properties" in {
    val trainer = Trainer(position, idTrainer, pokemonTeam)
    trainer.position should be(position)
    trainer.id should be(idTrainer)
    trainer.pokemonTeam should be(pokemonTeam)
  }

  it should "have visibility as a VisibleEntity" in {
    val visibleEntity: VisibleEntity = player
    visibleEntity should be(player)
  }

  it should "create a Player with the correct properties" in {
    player.position should be(position)
    player.id should be(idTrainer)
    player.pokemonTeam should be(pokemonTeam)
    player.bag.items should be(empty)
  }

  it should "update the pokemonTeam correctly" in {
    val updatedPokemonList: Seq[Pokemon] = Seq(Pokemon("3", "Charmander", 50, 52, 43, 65, List(), ElementType.Fire),
      Pokemon("4", "Squirtle", 50, 48, 65, 43, List(), ElementType.Water))

    player = player withPokemon(updatedPokemonList)
    player.pokemonTeam should be(updatedPokemonList)
  }

  it should "update the position correctly" in {
    val newPosition = Position(9, 9)
    player = player withPosition newPosition
    player.position should be(newPosition)
  }

  it should "update the player's bag correctly" in {
    val item = ItemFactory(ItemType.Potion)
    player.bag.addItem(item)
    player.bag.items should contain(item)
  }

  it should "update the id correctly" in {
    val rightDirection = "right"
    player = player movesTo rightDirection
    player.id should be(rightDirection)
  }