package controller

import controller.events.{EndBattle, Event, OptionChosen, StartGame,CollisionEvent}
import model.battle.Battle
import model.battle.cpu.Cpu
import model.entities.pokemon.Pokemon
import model.entities.{Entity, Player, Trainer, VisibleEntity, World, Item, Door}
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

  override def eventHandler(e: Event): Unit = e match
    case visibleEntity:CollisionEvent => visibleEntity.entity match
      case trainer: Trainer => BattleController.startBattle(model.player, trainer)
      case item: Item => model.itemCollision(item)
      case door: Door => println("Collision with Door") // check se gli opp == 0 e nextLevel()

  def removeTrainer(id: String): Unit = println("Terminato"); handleScreenChange(screen)

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
      case e: OptionChosen =>
          screen.asInstanceOf[BattleScreen].battleScreenUpdate(model.takeTurn(e.battleOption))
      case e: EndBattle =>
        if e.trainerId == model.player.id then
          {/*todo call gameOver from gameController*/}
        else
          GameController.removeTrainer(e.trainerId)