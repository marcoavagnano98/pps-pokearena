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

  def apply(gridW: Int, gridH: Int): Grid = GridImpl(gridW, gridH)

    private case class GridImpl(gridW: Int, gridH: Int) extends Grid:
      private val playerPosition = Position(0, 0)
      private val doorPosition = Position(4, 9)
      private var allLevelGridPositions: Seq[Position] = computeAllLevelGridPositions(gridW, gridH).filterNot(pos => pos == playerPosition || pos == doorPosition)

      override def getRandomPos: Position =
        val newPositions: Seq[Position] = Random.shuffle(allLevelGridPositions)
        val position = newPositions.head
        updateAvailablePositions(newPositions.drop(1))
        position

      override def updateAvailablePositions(pos: Seq[Position]): Unit =
        allLevelGridPositions = pos

      override def allAvailablePositions: Seq[Position] = allLevelGridPositions

      private def computeAllLevelGridPositions(gridW: Int, gridH: Int): Seq[Position] = for
        row <- 0 until gridH
        col <- 0 until gridW
      yield Position(col, row)

