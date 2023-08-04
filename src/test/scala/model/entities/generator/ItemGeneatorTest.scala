package model.entities.generator
import model.entities.World.Position
import model.entities.pokemon.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers
import math.pow
import util.Grid

class ItemGeneatorTest extends AnyFlatSpec with Matchers:
  private val gridDimension = 10
  private val doorPosition = Position(5, 5)
  private var grid = Grid(gridDimension, doorPosition)
  private val numberOfItemsToGenerate = 3
  private val tryToGenerateNumberOfItems = 110
  private var items = ItemGenerator(grid, numberOfItemsToGenerate)

  "ItemGenerator" should "generate the specified number of items" in {
    items should have size numberOfItemsToGenerate
  }

  it should "generate items with different and available positions" in {
    val positions = items.map(_.position)
    positions.foreach(position => grid.allAvailablePositions should not contain position)
  }

  it should "generate random items like potion and superPotion" in {
    val potionCount = items.count(_.name == "Potion")
    val superPotionCount = items.count(_.name == "Super Potion")
    potionCount should be >= 1
    superPotionCount should be >= 1
  }

  it should "not generate any items when the available positions are finished" in {
    items = Seq.empty
    grid = Grid(gridDimension, doorPosition)
    items = ItemGenerator(grid, tryToGenerateNumberOfItems)
    items should have size ((gridDimension*gridDimension)-2)
  }