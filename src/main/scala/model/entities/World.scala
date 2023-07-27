package model.entities

import model.entities.pokemon.{Pokemon, PokemonFactory}
import com.badlogic.gdx.Gdx
import controller.events.{CollisionEvent, EventDispatcher}
import util.Stats

import scala.annotation.tailrec
import scala.util.Random

trait World:
  def createLevel(pokemonTeam: Seq[Pokemon]): Unit
  def level: Level
  def player_=(player: Player): Unit
  def player: Player
  def visibleEntities: Seq[VisibleEntity]
  def checkCollision: Option[VisibleEntity]
  def itemCollision(item: Item): Unit
  def doorCollision(door: Door): Unit
  def removeTrainer(idTrainer: String): Unit
  def updateDoor: Unit
  def difficulty: Int
  def difficulty_=(difficulty: Int): Unit
  def room: Int
  def gameEnded: Boolean
  def stats: Stats

object World:
  def apply(): World = WorldImpl()

  private class WorldImpl extends World:
    private val idLevel = "map_"
    private val idPlayer = "player"
    private val openDoor = "door_open"
    private val numberOfMaps = 13
    private var _difficulty = 0
    private var _levelRoom = 1
    private var playerDefeated = 0
    private var _gameEnded = false
    private var _level: Level = _
    private var _player: Player = Player(Position(0, 0), idPlayer, Seq.empty)
    private val _statistics = Stats()

    override def createLevel(pokemonTeam: Seq[Pokemon]): Unit =
      _player = _player withPokemon pokemonTeam
      _level = Level(idLevel+Random.between(0, numberOfMaps))
      _level.generateEntities(_levelRoom)

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
          _levelRoom += 1
          _levelRoom match
            case room if room <= 4 =>
              createLevel(player.pokemonTeam)
              _player = _player.withPosition(Position(0, 0))
            case _ =>
              _gameEnded = true
        case _ =>

    override def removeTrainer(idTrainer: String): Unit =
      _level.removeOpponent(idTrainer)
      playerDefeated += 1

    override def updateDoor: Unit =
      if _level.opponents.isEmpty then
        _level.door = _level.door.updateDoor(openDoor, DoorState.Open)

    override def difficulty: Int = _difficulty

    override def difficulty_=(difficulty: Int): Unit =
      _difficulty = difficulty

    override def room: Int = _levelRoom

    override def gameEnded: Boolean = _gameEnded

    override def stats: Stats = _statistics

  /**
   *  Position class represents the coordinates x,y in the World
   * @param x coordinate
   * @param y coordinate
   */
  case class Position(x: Double, y: Double)