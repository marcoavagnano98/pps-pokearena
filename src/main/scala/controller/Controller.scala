package controller

import controller.events.{CollisionEvent, EndBattle, EndGame, Event, OptionChosen, StartGame}
import model.battle.Battle
import model.battle.cpu.Cpu
import model.entities.pokemon.Pokemon
import model.entities.{Door, Entity, Item, Player, Trainer, VisibleEntity, World}
import pokearena.PokeArena
import view.screen.{BasicScreen, BattleScreen, GameScreen}

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

protected object MenuController extends Controller:
  override def eventHandler(e: Event): Unit = e match
    case e: StartGame =>
      GameController.startGame(e.list)
    case _ =>

protected object GameController extends Controller:

  override type T = World

  model = World()

  override def eventHandler(e: Event): Unit = e match
    case visibleEntity: CollisionEvent => visibleEntity.entity match
      case trainer: Trainer => BattleController.startBattle(model.player, trainer)
      case item: Item => model.itemCollision(item)
      case door: Door => model.doorCollision(door)
    case _  => endGame()

  def removeTrainer(id: String): Unit =
    model.removeTrainer(id)
    handleScreenChange(screen)

  def startGame(pokemonList: Seq[Pokemon]): Unit =
    model.createLevel(pokemonList)
    screen = GameScreen(model)
    handleScreenChange(screen)

  def endGame(): Unit =
    /*screen = GameScreen(model) /*TODO: change to StatsScreen*/
    handleScreenChange(screen)*/
    println("Fine gioco")

object BattleController extends Controller:
  override type T = Battle

  def startBattle(player: Player, opponent: Trainer): Unit =
    model = Battle(player, opponent)
    screen = BattleScreen(model)
    handleScreenChange(screen)

  override def eventHandler(e: Event): Unit =
    e match
      case e: OptionChosen =>
          screen.asInstanceOf[BattleScreen].battleScreenUpdate(model.playRound(e.battleOption))
          
      case e: EndBattle =>
        if e.trainerId == model.player.id then
          GameController.endGame()
          //println("FIne gioco")
        else
          GameController.removeTrainer(e.trainerId)