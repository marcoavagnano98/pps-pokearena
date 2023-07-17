package model.entities

import model.entities.pokemon.{Pokemon, PokemonFactory}
import util.Drawable
import view.Sprites.getMapSprite
import com.badlogic.gdx.Gdx

import scala.annotation.tailrec

trait World:
  def player: Player
  def player_=(player: Player): Unit
  def opponents: Seq[Trainer]
  def potions: Seq[Potion]
  def generateEntities(pokemonTeam: Seq[Pokemon]): Unit
  def visibleEntities:Seq[VisibleEntity] = Seq[VisibleEntity](player) ++: opponents ++: potions
  def createMap(id: String): Unit
  def gameMap: Map
  def playerSpeed: Int

object World:
  def apply(): World = WorldImpl()

  private class WorldImpl extends World:
    private var _map: Map = _
    private val gameXPos: Float = 0.0
    private val gameYPos: Float = 0.0
    private val width: Int = Gdx.graphics.getWidth
    private val height: Int = Gdx.graphics.getHeight
    private val desiredGridSize: Int = 100
    private val cellSize: Int =
      val rows = Math.ceil(height.toDouble/desiredGridSize).toInt
      val cols = Math.ceil(width.toDouble/desiredGridSize).toInt
      Math.min(width/cols, height/rows)
    private val gridWidth = width/cellSize
    private val gridHeight = height/cellSize
    private val speed: Int = cellSize

    private var _player: Player = Player(Position(0, 0), "player", Seq.empty)
    private var _opponents: Seq[Trainer] = Seq.empty
    private var _potions: Seq[Potion] = Seq.empty

    override def createMap(id: String): Unit = _map = Map(getMapSprite(id), gameXPos, gameYPos, width, height)
    override def gameMap: Map = _map
    override def playerSpeed: Int = speed
    override def player_=(player: Player ): Unit = _player = player
    override def player: Player = _player
    override def opponents: Seq[Trainer] = _opponents
    override def potions: Seq[Potion] = _potions
    override def generateEntities(pokemonTeam: Seq[Pokemon]): Unit =
      _player = _player withPokemon pokemonTeam
      val (opps, pots): (Seq[Trainer], Seq[Potion]) = generateTrainer(3)
      _opponents = opps
      _potions = pots

    private def generateTrainer(numberOfTrainer: Int): (Seq[Trainer], Seq[Potion]) =
      @tailrec
      def _generateTrainersAndPotions(trainerList: List[Trainer], potionList: List[Potion], numberOfTrainer: Int): (Seq[Trainer], Seq[Potion]) = (trainerList, potionList) match
          case (t, p) if numberOfTrainer > 0 => _generateTrainersAndPotions(t :+ Trainer(id = "op" + numberOfTrainer, pos = randomPos, pokemonList = PokemonFactory(2)), p :+ Potion(position = randomPos), numberOfTrainer - 1)
          case _ => (trainerList, potionList)
      _generateTrainersAndPotions(List[Trainer](), List[Potion](), numberOfTrainer)

    /*TODO: aggiungere controllo sulla posizione 900,400(?) per la porta */
    private def randomPos: Position =
      val allPositions: Seq[Position] = for
        row <- 0 until gridHeight
        col <- 0 until gridWidth
      yield Position(col * cellSize, row * cellSize)

      @tailrec
      def findValidPosition(remainingPositions: List[Position]): Position =
        if (remainingPositions.isEmpty)
          println("No more positions available")
          Position(-1, -1)
        else
          import scala.util.Random
          val randomIndex = Random.nextInt(remainingPositions.length)
          val randomPosition = remainingPositions(randomIndex)

          if (randomPosition != _player.position && !_opponents.exists(_.position == randomPosition))
            randomPosition
          else
            findValidPosition(remainingPositions.filterNot(_ == randomPosition))

      findValidPosition(allPositions.toList)

  /**
   *  Position class represents the coordinates x,y in the World
   * @param x coordinate
   * @param y coordinate
   */
  case class Position(x: Double, y: Double)