package controller

import controller.events.{CollisionEvent, DisplayGameOverScreen, Event, OptionChosen, EndFight, StartGame, OpenDoor}
import model.battle.Battle
import model.battle.cpu.Cpu
import model.entities.pokemon.Pokemon
import model.entities.{Door, Item, Player, Trainer, VisibleEntity, World, GameStatus}
import pokearena.PokeArena
import util.Stats
import view.screen.{BasicScreen, BattleScreen, GameOverScreen, GameScreen}

/**
 * Manage the Event generated during the interactions with the Game
 */
protected[controller] trait Controller:

  type T

  var model: T = _

  var screen: BasicScreen = _

  /**
   * Set the model from where the data should be retrieved
   * @param data used as model
   */
  def setModel(data: T): Unit = model = data

  /**
   * Handle the input event
   * @param e the Event to be handled
   */
  def eventHandler(e: Event): Unit

  /**
   * Handle the screen to be displayed in the game
   * @param screen to be displayed
   */
  def handleScreenChange(screen: BasicScreen): Unit =
    PokeArena.changeScreen(screen)

/**
 * The Controller for the MenuScreen (PokemonChoiceScreen)
 */
protected object MenuController extends Controller :
  override def eventHandler(e: Event): Unit = e match
    case e: StartGame =>
      GameController.startGame(e.list, e.difficulty)
    case _ =>

/**
 * The Controller for the GameScreen
 */
protected object GameController extends Controller :

  override type T = World
  import util.Stats
  private val stats = Stats()

  override def eventHandler(e: Event): Unit = e match
    case visibleEntityCollision: CollisionEvent => visibleEntityCollision.entity match
      case trainer: Trainer => BattleController.startBattle(model.player, trainer)
      case item: Item => model.itemCollision(item); stats.count(item)
      case door: Door => model.doorCollision(door)
    case _: OpenDoor => model.updateDoor
    case _ => endGame()

  /**
   * Remove the input Trainer from the Level
   * @param trainer to be removed
   */
  def removeTrainer(trainer: Trainer): Unit =
    model.removeTrainer(trainer)
    stats.count(trainer)
    handleScreenChange(screen)

  /**
   *
   * Create the Level and set the GameScreen as the current screen
   * @param pokemonList the Player's pokemon team
   * @param difficulty the difficulty of the Game
   */
  def startGame(pokemonList: Seq[Pokemon], difficulty: Int): Unit =
    setModel(World(difficulty))
    stats.reset()
    model.createLevel(pokemonList)
    screen = GameScreen(model)
    handleScreenChange(screen)

  /**
   * Handle the end of the game
   */
  def endGame(): Unit =
    stats.count(model.isGameWon)
    screen = GameOverScreen(model, stats)
    handleScreenChange(screen)

/**
 * The Controller for the BattleScreen
 */
object BattleController extends Controller :
  override type T = Battle

  /**
   * Handle the Battle between the Player and a Trainer
   * @param player involved in the battle
   * @param opponent involved in the battle
   */
  def startBattle(player: Player, opponent: Trainer): Unit =
    model = Battle(player, opponent)
    screen = BattleScreen(model)
    handleScreenChange(screen)

  override def eventHandler(e: Event): Unit =
    e match
      case e: OptionChosen =>
         screen.asInstanceOf[BattleScreen].battleScreenUpdate(model.playRound(e.battleOption))
      case _: EndFight =>
        model.pokemonInBattle match
          case (None, Some(_)) => GameController.endGame()
          case (Some(_), None) => GameController.removeTrainer(model.opponent)
          case _ =>