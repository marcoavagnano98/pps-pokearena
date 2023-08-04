package model.entities

import model.entities.pokemon.Pokemon
import com.badlogic.gdx.Gdx
import prolog.PrologApp.BstGenerator
import util.Stats

import scala.annotation.tailrec
import scala.util.Random

/**
 * Represent the game state:
 * Win if you have defeated the boss in the last level;
 * Lose otherwise.
 */
enum GameStatus:
  case Win, Lose

/**
 * Represent the entire game world.
 * Within it, you can find information about the current Level and about the Player.
 */
trait World:
  /**
   * Create the current Level and provide the Player with the Pokemon team
   * @param pokemonTeam the Pokemon team of the Player
   */
  def createLevel(pokemonTeam: Seq[Pokemon]): Unit

  /**
   *
   * @return the current Level with the informations about [[Trainer]], [[Item]] and [[Door]]
   */
  def level: Level

  /**
   *
   * @param player update the [[Player]] informations
   */
  def player_=(player: Player): Unit

  /**
   *
   * @return retrive the [[Player]] informations
   */
  def player: Player

  /**
   *
   * @return all the [[VisibleEntities]] to be displayed in the Screen
   */
  def visibleEntities: Seq[VisibleEntity]

  /**
   *
   * @return the [[VisibleEntity]] with which a collision has occurred
   */
  def checkCollision: Option[VisibleEntity]

  /**
   *
   * @param item the Item with which a collision has occurred
   */
  def itemCollision(item: Item): Unit

  /**
   *
   * @param door the [[Door]] with which a collision has occurred
   */
  def doorCollision(door: Door): Unit

  /**
   *
   * @param trainer the [[Trainer]] with which a collision has occurred
   */
  def removeTrainer(trainer: Trainer): Unit

  /**
   * check if all the [[Trainer]] are defeated and updates the Door
   */
  def updateDoor: Unit

  /**
   *
   * @return the current level
   */
  def currentLevel: Int

  /**
   *
   * @return the status of the game (Win/Lose)
   */
  def isGameWon: GameStatus

object World:
  def apply(difficulty: Int = 1, maxLevel: Int = 4): World = WorldImpl(difficulty, maxLevel)

  private class WorldImpl(val difficulty: Int, val maxLevel: Int) extends World:
    
    private val bstIterator = BstGenerator.generate(difficulty, maxLevel)

    private val idPlayer = "player"
    private val openDoor = "door_open"
    private var _currentLevel = 1
    private var _isGameWon = GameStatus.Lose
    private var _level: Level = _
    private var _player: Player = Player(Position(0, 0), idPlayer, Seq.empty)

    override def createLevel(pokemonTeam: Seq[Pokemon]): Unit =
      _player = _player withPokemon pokemonTeam
      _level = Level(_currentLevel, maxLevel, bstIterator.next())

    override def level: Level = _level
    override def player_=(player: Player ): Unit = _player = player
    override def player: Player = _player
    override def visibleEntities: Seq[VisibleEntity] = Seq[VisibleEntity](_level.door) ++: _level.opponents ++: _level.items :+ player

    override def checkCollision: Option[VisibleEntity] =
      visibleEntities.find(e => e.position == player.position && e != player)

    override def itemCollision(item: Item): Unit =
      player.bag.addItem(item)
      _level.removeItem(item)

    override def doorCollision(door: Door): Unit =
      _level.door.state match
        case DoorState.Open =>
          if _currentLevel < maxLevel then
              _currentLevel += 1
              createLevel(player.pokemonTeam)
              _player = _player.withPosition(Position(0, 0))
          else
              _isGameWon = GameStatus.Win
        case _ =>

    override def removeTrainer(trainer: Trainer): Unit =
      _level.removeOpponent(trainer)

    override def updateDoor: Unit =
      _level.door = _level.door.updateDoor(openDoor, DoorState.Open)

    override def currentLevel: Int = _currentLevel

    override def isGameWon: GameStatus = _isGameWon


  /**
   * Represent the coordinates (x, y) in the game world.
   * @param x coordinate
   * @param y coordinate
   */
  case class Position(x: Float, y: Float)