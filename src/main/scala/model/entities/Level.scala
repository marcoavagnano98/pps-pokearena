package model.entities

import model.entities.World.Position
import model.entities.pokemon.{Pokemon, PokemonFactory}
import scala.annotation.tailrec
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
  def removeOpponent(trainer: Trainer): Unit
  def door: Door
  def door_=(door: Door): Unit
  def generateEntities(currentLevel: Int, maxLevel: Int): Unit

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
    private val playerPosition = Position(0,0)
    private val opponentIdsNumber = 22
    private val numberOfEntitiesToGenerate = 3
    private var allOpponents: Seq[Int] = Seq.empty
    private var allPositions: Seq[Position] = for
      row <- 0 until gridHeight
      col <- 0 until gridWidth
    yield Position(col, row)

    override def opponents: Seq[Trainer] = _opponents

    override def items: Seq[Item] = _items

    override def removeItem(item: Item): Unit =
      _items = _items.filter(_!=item)

    override def removeOpponent(trainer: Trainer): Unit =
      _opponents = _opponents.filterNot(_ == trainer)

    override def door: Door = _door

    override def door_=(door: Door): Unit =
      _door = door

    override def generateEntities(currentLevel: Int, maxLevel: Int): Unit = currentLevel match
      case `maxLevel` => val boss: Trainer = generateBoss
        _opponents = Seq(boss)
      case _ => val (opps, itms): (Seq[Trainer], Seq[Item]) = generateTrainersAndItems(numberOfEntitiesToGenerate)
        _opponents = opps
        _items = itms

    private def generateTrainersAndItems(numberOfEntities: Int): (Seq[Trainer], Seq[Item]) =
      @tailrec
      def _generateTAndI(trainerList: Seq[Trainer], itemList: Seq[Item], numberOfTrainer: Int): (Seq[Trainer], Seq[Item]) = (trainerList, itemList) match
        case (t, p) if numberOfTrainer > 0 => _generateTAndI(t :+ Trainer(id = "op" + randomOpponent, pos = randomPos, pokemonList = PokemonFactory(2)), p :+ ItemFactory.getRandomItem(pos = randomPos), numberOfTrainer - 1)
        case _ => (trainerList, itemList)

      _generateTAndI(Seq[Trainer](), Seq[Item](), numberOfEntities)

    private def generateBoss: Trainer = Trainer(id = "boss", pos = Position(4,5), pokemonList = PokemonFactory(4))

    allPositions = allPositions.filterNot(pos => pos == playerPosition || pos == _door.position)

    @tailrec
    private def randomOpponent: Int =
      val opp = Random.between(0, opponentIdsNumber)
      opp match
        case opp if !allOpponents.contains(opp) =>
          allOpponents = allOpponents :+ opp
          opp
        case _ => randomOpponent

    private def randomPos: Position =
      val newPosition = Random.shuffle(allPositions)
      val pos = newPosition.head
      allPositions = newPosition.drop(1)
      pos



