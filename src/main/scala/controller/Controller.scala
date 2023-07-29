package controller

import controller.events.{CollisionEvent, EndGame, Event, OptionChosen, PokemonDefeated, StartGame}
import model.battle.Battle
import model.battle.cpu.Cpu
import model.entities.pokemon.Pokemon
import model.entities.{Door, Entity, Item, Player, Trainer, VisibleEntity, World, GameStatus}
import pokearena.PokeArena
import util.Stats
import view.screen.{BasicScreen, BattleScreen, GameOverScreen, GameScreen}

/*
* All methods that handle internal screen events must be private
* */

protected[controller] trait Controller:

  type T

  var model: T = _

  var screen: BasicScreen = _

  def setScreen(basicScreen: BasicScreen): Unit = screen = basicScreen

  def setModel(data: T): Unit = model = data

  def eventHandler(e: Event): Unit

  def handleScreenChange(screen: BasicScreen): Unit =
    PokeArena.changeScreen(screen)

protected object MenuController extends Controller :
  override def eventHandler(e: Event): Unit = e match
    case e: StartGame =>
      GameController.startGame(e.list)
    case _ =>

protected object GameController extends Controller :

  override type T = World
  import util.Stats
  private val stats = Stats()

  override def eventHandler(e: Event): Unit = e match
    case visibleEntityCollision: CollisionEvent => visibleEntityCollision.entity match
      case trainer: Trainer => BattleController.startBattle(model.player, trainer)
      case item: Item => {model.itemCollision(item); stats.count(item)}
      case door: Door => model.doorCollision(door)
    case _ => endGame()

  def removeTrainer(trainer: Trainer): Unit =
    model.removeTrainer(trainer)
    model.updateDoor
    stats.count(trainer)
    handleScreenChange(screen)

  def startGame(pokemonList: Seq[Pokemon]): Unit =
    setModel(World())
    stats.reset()
    model.createLevel(pokemonList)
    screen = GameScreen(model)
    handleScreenChange(screen)

  def endGame(): Unit =
    stats.count(model.isGameWon)
    screen = GameOverScreen(stats, model.room, model.player.pokemonTeam)
    handleScreenChange(screen)

object BattleController extends Controller :
  override type T = Battle

  def startBattle(player: Player, opponent: Trainer): Unit =
    model = Battle(player, opponent)
    screen = BattleScreen(model)
    handleScreenChange(screen)

  override def eventHandler(e: Event): Unit =
    e match
      case e: OptionChosen =>
         screen.asInstanceOf[BattleScreen].battleScreenUpdate(model.playRound(e.battleOption))
      case _: PokemonDefeated =>
        model.pokemonInBattle match
          case (None, Some(_)) => GameController.endGame()
          case (Some(_), None) => GameController.removeTrainer(model.opponent)
          case _ =>