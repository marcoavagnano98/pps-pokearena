package model.entities

import model.entities.pokemon.{Pokemon, PokemonFactory}
import util.Drawable
import view.Sprites.getMapPath
import com.badlogic.gdx.Gdx
import controller.events.{CollisionEvent, EventDispatcher}

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
  def difficulty: Int
  def difficulty_=(difficulty: Int): Unit
  def room: Int
  def gameEnded: Boolean


object World:
  def apply(): World = WorldImpl()

  private class WorldImpl extends World:
    private final val idLevel = "map_"
    private var _level: Level = _
    private var _player: Player = Player(Position(0, 0), "player", Seq.empty)
    private var _difficulty = 0
    private var _levelRoom = 1
    private var _gameEnded = false

    override def createLevel(pokemonTeam: Seq[Pokemon]): Unit =
      _player = _player withPokemon pokemonTeam
      _level = Level(getMapPath(idLevel))
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
      if _level.door.state.equals(DoorState.Open) then
        _levelRoom += 1
        if _levelRoom <=4 then
          createLevel(player.pokemonTeam)
          _player = _player withPosition Position(0, 0)
        else
          _gameEnded = true


    override def removeTrainer(idTrainer: String): Unit =
      _level.removeOpponent(idTrainer)

    override def difficulty: Int = _difficulty

    override def difficulty_=(difficulty: Int): Unit =
      _difficulty = difficulty

    override def room: Int = _levelRoom

    override def gameEnded: Boolean = _gameEnded

  /**
   *  Position class represents the coordinates x,y in the World
   * @param x coordinate
   * @param y coordinate
   */
  case class Position(x: Double, y: Double)