package model.entities.generator
import model.entities.World.Position
import model.entities.pokemon.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers
import util.Grid

class TrainerGeneratorTest extends AnyFlatSpec with Matchers:
  private val gridDimension = 10
  private val doorPosition = Position(5, 5)
  private var grid = Grid(gridDimension, doorPosition)
  private val numberOfTrainersToGenerate = 3
  private val tryToGenerateNumberOfTrainers = 110
  private val bst = (100, 200)
  private var trainers = TrainerGenerator(grid, numberOfTrainersToGenerate, bst)

  "TrainerGenerator" should "generate the specified number of trainers" in {
    trainers should have size numberOfTrainersToGenerate
  }

  it should "generate trainers with different and available positions" in {
    val positions = trainers.map(_.position)
    positions.foreach(position => grid.allAvailablePositions should not contain(position))
  }

  it should "generate trainers with the correct number of Pokemon based on the BST range" in {
    val numberOfPokemons = trainers.map(_.pokemonTeam.size)
    numberOfPokemons.foreach(numberOfPokemonsTrainer => numberOfPokemonsTrainer should be(2))
    val bstPokemonTrainers = trainers.flatMap(_.pokemonTeam.map(pokemon => pokemon.bst))
    bstPokemonTrainers.foreach(bstPokemon => bstPokemon should (be >= bst._1 and be <= bst._2))
  }

  it should "not generate any trainers when the available positions are finished" in {
    trainers = Seq.empty
    grid = Grid(gridDimension, doorPosition)
    trainers = TrainerGenerator(grid, tryToGenerateNumberOfTrainers, bst)
    trainers should have size ((gridDimension * gridDimension) - 2)
  }
