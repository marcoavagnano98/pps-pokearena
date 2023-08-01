package model.entities

import model.entities.World.Position
import model.entities.generator.{ItemGenerator, PokemonGenerator, TrainerGenerator}
import model.entities.pokemon.Pokemon
import util.Grid

import scala.annotation.tailrec
import scala.util.Random

/**
 * Represent a Level in the Game
 */
trait Level:
  /**
   *
   * @return the unique file name that identifies the background of the level
   */
  def idLevel: String

  /**
   *
   * @return the x-coordinate from which the level should start rendering
   */
  def levelXPos: Float

  /**
   *
   * @return the y-coordinate from which the level should start rendering
   */
  def levelYPos: Float

  /**
   *
   * @return the dimension of the level, as width and height
   */
  def gridDimension: Int


  /**
   *
   * @return the size of a cell. It identifies the dimension of the cells that will compose the grid for the [[Player]]'s movement
   */
  def cellSize: Int

  /**
   *
   * @return the amount by which the [[Player]] should move within the grid
   */
  def playerSpeed: Int

  /**
   *
   * @return the sequence of opponents [[Trainer]] that need to be created in the [[Level]]
   */
  def opponents: Seq[Trainer]

  /**
   *
   * @return the sequence of Items that need to be created in the [[Level]]
   */
  def items: Seq[Item]

  /**
   *
   * @param item that should be deleted from the [[Level]]
   */
  def removeItem(item: Item): Unit

  /**
   *
   * @param trainer tha should be deleted from the [[Level]]
   */
  def removeOpponent(trainer: Trainer): Unit

  /**
   *
   * @return the the [[Door]] in the level
   */
  def door: Door

  /**
   *
   * @param door that should be updated after an event
   */
  def door_=(door: Door): Unit

object Level:
  def apply(currentLevel:Int, maxLevel: Int, bstRange: (Int, Int), gridDimension: Int = 10, numberOfTrainersToGenerate: Int = 0, numberOfItemsToGenerate: Int = 3): Level =
    LevelImpl(gridDimension, numberOfTrainersToGenerate, numberOfItemsToGenerate, currentLevel, maxLevel, bstRange)

  private case class LevelImpl(override val gridDimension: Int,
                               numberOfTrainersToGenerate: Int,
                               numberOfItemsToGenerate: Int,
                               currentLevel: Int,
                               maxLevel: Int,
                               bstRange: (Int, Int),
                               override val levelXPos: Float = 0.0,
                               override val levelYPos: Float = 0.0,
                               override val cellSize: Int = 1,
                               override val playerSpeed: Int = 1,
                               ) extends Level:

    private var _door: Door = Door(DoorState.Close, Position((gridDimension*0.5).toInt, gridDimension-1))
    private val _grid: Grid = Grid(gridDimension, _door.position)
    private val numberOfLevelsBackground = 13
    private var (_opponents, _items) = generateEntities(currentLevel,maxLevel)
    override val idLevel: String = "map_" + Random.between(0, numberOfLevelsBackground)

    override def opponents: Seq[Trainer] = _opponents

    override def items: Seq[Item] = _items

    override def removeItem(item: Item): Unit =
      _items = _items.filter(_!=item)

    override def removeOpponent(trainer: Trainer): Unit =
      _opponents = _opponents.filterNot(_ == trainer)

    override def door: Door = _door

    override def door_=(door: Door): Unit =
      _door = door

    private def generateEntities(currentLevel: Int, maxLevel: Int): (Seq[Trainer], Seq[Item]) = currentLevel match
      case `maxLevel` =>
        (Seq(generateBoss), Seq[Item]())
      case _ =>
        (TrainerGenerator(_grid, numberOfTrainersToGenerate, bstRange), ItemGenerator(_grid, numberOfItemsToGenerate))

    private def generateBoss: Trainer = Trainer(id = "boss", pos = Position(4,5), pokemonList = PokemonGenerator.getPokemonByBstRange(bstRange,4))