package controller

import controller.events.{EndBattle, Event, OptionChosen, StartGame}
import model.battle.Battle
import model.battle.cpu.Cpu
import model.entities.pokemon.Pokemon
import model.entities.{Entity, Player, Trainer, VisibleEntity, World}
import pokearena.PokeArena
import view.screen.{BasicScreen, BattleScreen, GameScreen}

/*
* All methods that handle internal screen events must be private
* */

trait Controller:

  type T


  var model: T = _

  var screen: BasicScreen = _

  def setScreen(basicScreen: BasicScreen): Unit = screen = basicScreen

  def setModel(data: T): Unit = model = data

  def eventHandler(e: Event): Unit

  def handleScreenChange(screen: BasicScreen): Unit =
    PokeArena.changeScreen(screen)

protected object MenuController extends Controller:
  override def eventHandler(e: Event): Unit = e match
    case e: StartGame =>
      GameController.startGame(e.list)
    /* TODO: Create GameScreen and pass it the model and call handleScreenChange*/
    /* TODO: generate game model, list of Trainer and Items on the map*/
    case _ =>

protected object GameController extends Controller:

  override type T = World

  model = World()

  override def eventHandler(e: Event): Unit = ???

  def removeTrainer(id: String): Unit = ??? /* TODO: remove the current trainer from the visible entities list */

  def startGame(pokemonList: Seq[Pokemon]): Unit =
    model.createMap("map_")
    model.generateEntities(pokemonList)
    screen = GameScreen(model)
    handleScreenChange(screen)

object BattleController extends Controller:
  override type T = Battle
  def startBattle(player: Player, opponent: Trainer): Unit =
    model = Battle(player, opponent)
    screen = BattleScreen(model)
    handleScreenChange(screen)

  override def eventHandler(e: Event): Unit =
    e match
      case e: OptionChosen => model.takeTurn(e.battleOption, Cpu(model.pokemonInBattle._1.get, model.pokemonInBattle._2.get).optionChosen)
      case e: EndBattle => GameController.removeTrainer(e.trainerId); handleScreenChange(GameController.screen)

    screen match
      case bs : BattleScreen => screen.asInstanceOf[BattleScreen].updateView