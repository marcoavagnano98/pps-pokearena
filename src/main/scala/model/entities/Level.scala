package model.entities
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import model.entities.World.Position
import model.entities.pokemon.{Pokemon, PokemonFactory}

import scala.annotation.tailrec
import scala.collection.immutable.Set
import scala.util.Random
trait Level:
  def background: String
  def levelXPos: Float
  def levelYPos: Float
  def gridWidth: Int
  def gridHeight: Int
  def cellSize: Int
  def playerSpeed: Int
  def opponents: Seq[Trainer]
  def items: Seq[Item]
  def removeItem(item: Item): Unit
  def removeOpponent(idTrainer: String): Unit
  def door: Door
  def door_=(door: Door): Unit
  def generateEntities(levelRoom: Int): Unit

object Level:
  def apply(path: String): Level =
    LevelImpl(path)

  private case class LevelImpl(background: String,
                               override val levelXPos: Float = 0.0,
                               override val levelYPos: Float = 0.0,
                               override val gridWidth: Int = 10,
                               override val gridHeight: Int = 10,
                               override val cellSize: Int = 1,
                               override val playerSpeed: Int = 1) extends Level:

    private var _opponents: Seq[Trainer] = Seq.empty
    private var _items: Seq[Item] = Seq.empty
    private var _door: Door = Door(DoorState.Close, Position(4, 9))
    private final val playerPosition = Position(0,0) //???
    private final val numberOfEntitiesToGenerate = 3

    override def opponents: Seq[Trainer] = _opponents
    override def items: Seq[Item] = _items

    override def removeItem(item: Item): Unit =
      _items = _items.filter(_!=item)

    override def removeOpponent(idTrainer: String): Unit =
      _opponents = _opponents.filterNot(_.id == idTrainer)

    override def door: Door = _door

    override def door_=(door: Door): Unit =
      _door = door

    override def generateEntities(levelRoom: Int): Unit = levelRoom match
      case 4 => val boss: Trainer = generateBoss
        _opponents = Seq(boss)
      case _ => val (opps, itms): (Seq[Trainer], Seq[Potion]) = generateTrainersAndItems(numberOfEntitiesToGenerate)
        _opponents = opps
        _items = itms

    private def generateTrainersAndItems(numberOfEntities: Int): (Seq[Trainer], Seq[Item]) =
      @tailrec
      def _generateTAndI(trainerList: List[Trainer], itemList: List[Item], numberOfTrainer: Int): (Seq[Trainer], Seq[Item]) = (trainerList, itemList) match
        case (t, p) if numberOfTrainer > 0 => _generateTAndI(t :+ Trainer(id = "op" + randomOpponent, pos = randomPos, pokemonList = PokemonFactory(2)), p :+ ItemFactory.getRandomItem(pos = randomPos), numberOfTrainer - 1)
        case _ => (trainerList, itemList)

      _generateTAndI(List[Trainer](), List[Item](), numberOfEntities)

    private def generateBoss: Trainer = Trainer(id = "boss", pos = Position(4,5), pokemonList = PokemonFactory(4))

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

          if (randomPosition != playerPosition && !_opponents.exists(_.position == randomPosition) && !_items.exists(_.position == randomPosition))
            randomPosition
          else
            findValidPosition(remainingPositions.filterNot(_ == randomPosition))

      findValidPosition(allPositions.toList)



