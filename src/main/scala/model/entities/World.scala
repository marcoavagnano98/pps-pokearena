package model.entities

import model.entities.pokemon.{Pokemon, PokemonFactory}
import util.Drawable
import view.Sprites.getMapPath
import com.badlogic.gdx.Gdx
import controller.events.{CollisionEvent, EventDispatcher}

import scala.annotation.tailrec
import scala.util.Random

trait World:
  def createMap(id: String): Unit
  def gameMap: Map
  def playerSpeed: Int
  def player_=(player: Player): Unit
  def player: Player
  def opponents: Seq[Trainer]
  def items: Seq[Item]
  def door: Door
  def generateEntities(pokemonTeam: Seq[Pokemon]): Unit
  def visibleEntities:Seq[VisibleEntity] = Seq[VisibleEntity](door) ++: opponents ++: items :+ player
  def gridWidth : Int
  def gridHeight : Int
  def checkCollision: Option[VisibleEntity]
  def itemCollision(item: Item): Unit
  def doorCollision(door: Door): Unit
  def removeTrainer(idTrainer: String): Unit

object World:
  def apply(): World = WorldImpl()

  private class WorldImpl extends World:
    private var _map: Map = _
    private val mapXPos: Float = 0.0
    private val mapYPos: Float = 0.0
    val gridWidth: Int = 10
    val gridHeight: Int = 10
    private val cellSize = 1
    private val speed: Int = cellSize
    private var _player: Player = Player(Position(0, 0), "player", Seq.empty)
    private var _opponents: Seq[Trainer] = Seq.empty
    private var _items: Seq[Item] = Seq.empty
    private var _door: Door = Door(DoorState.Close, Position(4,9))

    override def createMap(id: String): Unit = _map = Map(getMapPath(id), mapXPos, mapYPos, gridWidth, gridHeight)
    override def gameMap: Map = _map
    override def playerSpeed: Int = speed
    override def player_=(player: Player ): Unit = _player = player
    override def player: Player = _player
    override def opponents: Seq[Trainer] = _opponents
    override def items: Seq[Item] = _items

    override def door: Door = _door

    override def generateEntities(pokemonTeam: Seq[Pokemon]): Unit =
      _player = _player withPokemon pokemonTeam
      val (opps, itms): (Seq[Trainer], Seq[Potion]) = generateTrainersAndItems(3)
      _opponents = opps
      _items = itms

    private def generateTrainersAndItems(numberOfTrainer: Int): (Seq[Trainer], Seq[Item]) =
      @tailrec
      def _generateTrainersAndPotions(trainerList: List[Trainer], itemList: List[Item], numberOfTrainer: Int): (Seq[Trainer], Seq[Item]) = (trainerList, itemList) match
        case (t, p) if numberOfTrainer > 0 => _generateTrainersAndPotions(t :+ Trainer(id = "op" + randomOpponent, pos = randomPos, pokemonList = PokemonFactory(2)), p :+ ItemFactory.getRandomItem(pos = randomPos), numberOfTrainer - 1)
        case _ => (trainerList, itemList)
      _generateTrainersAndPotions(List[Trainer](), List[Item](), numberOfTrainer)

    private var generatedOpponents: Set[Int] = Set.empty
    private val opponentsNumber = 22

    private def randomOpponent: Int =
      generatedOpponents.size match
        case `opponentsNumber` =>
          generatedOpponents = Set.empty
        case _ =>

      val availableOpponents = (0 until opponentsNumber).filterNot(generatedOpponents.contains)
      val randomOp = availableOpponents(Random.between(0, availableOpponents.length))
      generatedOpponents += randomOp
      randomOp

    private def randomPos: Position =
      val allPositions: Seq[Position] = for
        row <- 0 until gridHeight
        col <- 0 until gridWidth
      yield Position(col, row)

      @tailrec
      def findValidPosition(remainingPositions: List[Position]): Position =
        if (remainingPositions.isEmpty)
          println("No more positions available")
          Position(-1, -1)
        else
          import scala.util.Random
          val filteredPositions = remainingPositions.filterNot(_ == _door.position)
          val randomIndex = Random.nextInt(filteredPositions.length)
          val randomPosition = filteredPositions(randomIndex)

          if (randomPosition != _player.position && !_opponents.exists(_.position == randomPosition) && !_items.exists(_.position == randomPosition))
            randomPosition
          else
            findValidPosition(remainingPositions.filterNot(_ == randomPosition))

      findValidPosition(allPositions.toList)

    override def checkCollision: Option[VisibleEntity] =
      visibleEntities.find(e => e.position == player.position && e != player)

    override def itemCollision(item: Item): Unit =
      player.bag.addItem(item)
      _items = _items.filter(_ != item)

    override def doorCollision(door: Door): Unit =
      if _opponents.isEmpty then println("Create new Level")

    override def removeTrainer(idTrainer: String): Unit =
      _opponents = _opponents.filterNot(_.id == idTrainer)

  /**
   *  Position class represents the coordinates x,y in the World
   * @param x coordinate
   * @param y coordinate
   */
  case class Position(x: Double, y: Double)