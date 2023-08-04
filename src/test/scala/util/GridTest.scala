package util
import model.entities.World.Position
import model.entities.pokemon.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers

class GridTest extends AnyFlatSpec with Matchers:
  private val gridDimension = 10
  private val playerPosition = Position(0,0)
  private val doorPosition = Position(5,5)
  private val grid = Grid(gridDimension, doorPosition)
  private val availablePositions = (gridDimension * gridDimension) - 2

  it should "not contain the player position and the door position" in {
    grid.allAvailablePositions should not contain playerPosition
    grid.allAvailablePositions should not contain doorPosition
  }

  it should "update available positions after an entity is created" in {
    val initialAvailablePositions = grid.allAvailablePositions
    val positionToRemove = initialAvailablePositions.head
    initialAvailablePositions should have size (availablePositions)
    grid.updateAvailablePositions(initialAvailablePositions.drop(1))
    val updatedAvailablePositions = grid.allAvailablePositions
    updatedAvailablePositions should have size (availablePositions - 1)
    updatedAvailablePositions should not contain positionToRemove
  }

  it should "return a random position from all the available positions in the grid and remove it from the available" in {
    for i <- 1 to 10 do
      val randomPos = grid.getRandomPos
      grid.allAvailablePositions should not contain(randomPos)
  }




