package util

import model.entities.World.Position
import scala.util.Random

/**
 * The Grid in which the [[Level]] is divide to enable rendering of elements and the movement of the [[Player]]
 */
trait Grid:

  /**
   * update the available position after an [[Entity]] is created
   * @param pos the new Sequence of available [[Position]]
   */
  def updateAvailablePositions(pos: Seq[Position]): Unit

  /**
   *
   * @return a random [[Position]] from all the available Position in the [[Grid]]
   */
  def getRandomPos: Position

  /**
   *
   * @return the Sequence of all the available [[Position]] in the [[Grid]]
   */
  def allAvailablePositions: Seq[Position]

object Grid:

  def apply(gridDimension: Int, doorPosition: Position): Grid = GridImpl(gridDimension, doorPosition)

    private case class GridImpl(gridDimension: Int, doorPosition: Position) extends Grid:
      private val playerPosition = Position(0, 0)
      private var allLevelGridPositions: Seq[Position] = computeAllLevelGridPositions(gridDimension).filterNot(pos => pos == playerPosition || pos == doorPosition)

      override def getRandomPos: Position =
        val newPositions: Seq[Position] = Random.shuffle(allLevelGridPositions)
        val position = newPositions.head
        updateAvailablePositions(newPositions.drop(1))
        position

      override def updateAvailablePositions(pos: Seq[Position]): Unit =
        allLevelGridPositions = pos

      override def allAvailablePositions: Seq[Position] = allLevelGridPositions

      private def computeAllLevelGridPositions(gridDimension: Int): Seq[Position] = for
        row <- 0 until gridDimension
        col <- 0 until gridDimension
      yield Position(col, row)

