package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch, TextureRegion}
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.ui.{Container, HorizontalGroup, Image, ImageButton, ImageTextButton, Label, ProgressBar, Skin, Slider, Table, TextButton, TextField, VerticalGroup, Widget}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage, utils}
import com.badlogic.gdx.utils.viewport.{FitViewport, ScreenViewport, Viewport}
import model.entities.{Entity, ItemFactory, ItemId, Potion}
import com.badlogic.gdx.scenes.scene2d.utils.{ClickListener, TextureRegionDrawable}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.utils.{Align, Scaling, Timer}
import model.battle.{Battle, Status, TrainerChoice, Turn}
import view.{Sprites, screen}
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Timer.Task
import controller.events.{EndBattle, EventDispatcher, OptionChosen}
import model.battle.Status.*
import model.battle.TrainerChoice.*
import model.entities.World.Position
import model.entities.pokemon.{Move, PokemonFactory}
import pokearena.PokeArena
import view.battle.DialogueBox
import view.battle.layout.BattleMenuOption.{BagOption, FightOption}
import view.battle.layout.{BagLayout, BattleMenuBaseLayout, BattleMenuOption, FightLayout, PokemonInfoLayout}
import view.screen.Drawable

import scala.language.postfixOps



class BattleScreen(battle: Battle) extends BasicScreen :
  val skin: Skin = new Skin(Gdx.files.internal("assets/uiskin.json"))
  private def viewPortSize: (Float, Float) = (1000, 1000)
  override def viewport: FitViewport = FitViewport(viewPortSize._1, viewPortSize._2)

  val battleMenuLayout: BattleMenuBaseLayout = BattleMenuBaseLayout(Seq(menuTitle(battle.player.pokemonTeam.head.name)), skin, battleMenuRegion, menuLayoutAction)
  val fightLayout: FightLayout = FightLayout(battle.player.pokemonTeam.head, skin, battleMenuRegion, fightLayoutAction)
  val bagLayout: BagLayout = BagLayout(battle.player.bag, skin, battleMenuRegion, bagLayoutAction)
  val pPlayerInfoLayout: PokemonInfoLayout = PokemonInfoLayout(battle.player.pokemonTeam.head, skin, Rectangle(pBRegion.x, pBRegion.y,viewPortSize._1 / 2, 100))
  val pOpponentLayout: PokemonInfoLayout = PokemonInfoLayout(battle.opponent.pokemonTeam.head, skin, Rectangle(oBRegion.x, oBRegion.y, viewPortSize._1 / 2, 100))

  def backButton: ImageButton =
    val backButton = ImageButton(TextureRegionDrawable(TextureRegion(Texture("assets/backarrow.png"))))
    backButton.setPosition(battleMenuRegion.x + battleMenuRegion.width + 20, battleMenuRegion.y + (battleMenuRegion.height / 2))
    backButton.setSize(100, 100)
    backButton.addListener(new ClickListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        showBattleMenu()
        true
    })
    backButton

  private val showBattleMenu: () => Unit =
    () =>
      fightLayout.setVisible(false)
      bagLayout.setVisible(false)
      battleMenuLayout.setVisible(true)

  private def menuTitle(pokemonName: String): String = "Cosa deve fare " +  pokemonName + "?"

  def battleScreenUpdate(turnData: Seq[Turn]): Unit =
    showBattleMenu()
    battleMenuLayout.hideButtonMenu

    val titles: Seq[String] = Seq.from(
      for
        turn <- turnData
        description = turn.trainerChoice.description if turn.performed
        title = turn.pokemon.name + " " + description
      yield title
    ) concat (
      for
        turn <- turnData
        description <- turn.turnStatus.description
        title = turn.pokemon.name + " " + description
      yield title
      )
    battleMenuLayout.update(titles)
    Timer.schedule( new Task(){
      override def run(): Unit =
        battle.pokemonInBattle match
        case (Some(playerPokemon), Some(opponentPokemon)) =>
          fightLayout.update(playerPokemon)
          battleMenuLayout.update(Seq(menuTitle(playerPokemon.name)))
          bagLayout.update(battle.player.bag)
          pPlayerInfoLayout.update(playerPokemon)
          pOpponentLayout.update(opponentPokemon)
          battleMenuLayout.showButtonMenu
        case (Some(_), None) => sendEvent(EndBattle(battle.opponent.id))
        case _ => sendEvent(EndBattle(battle.player.id))
    }, 2)




  private def pBRegion: Rectangle =
    val width = viewPortSize._1
    val height = viewPortSize._2
    Rectangle(width / 5, (height / 2.5).toInt, 150, 150)

  private def oBRegion: Rectangle =
    val width = viewPortSize._1
    val height = viewPortSize._2
    Rectangle(width / 3, height - (height / 4), 150, 150)

  private def battleMenuRegion: Rectangle = Rectangle(
    viewPortSize._1 / 4, 50, viewPortSize._1 / 2, 200
  )

  private def menuLayoutAction(option: BattleMenuOption): Unit =
    option match
      case FightOption => fightLayout.setVisible(true)
      case _ => bagLayout.setVisible(true)
    battleMenuLayout.setVisible(false)

  private def fightLayoutAction(moveIndex: Int): Unit =
    val pokemon = battle.pokemonInBattle._1.get
    val move: Move = pokemon.moves(moveIndex)
    val fightOption: TrainerChoice = Attack(move)
    sendEvent(OptionChosen(fightOption))

  private def bagLayoutAction(itemIndex: Int): Unit =
    val item = battle.player.bag.items(itemIndex)
    battle.player.bag.removeItem(item)
    sendEvent(OptionChosen(UseBag(item)))

  override def actors: Seq[Actor] =
    Seq(background,
      battleMenuLayout,
      fightLayout,
      bagLayout,
      backButton,
      pPlayerInfoLayout,
      pOpponentLayout)
  private def background: Image =
    val image = Image(Texture("assets/battle-screen.png"))
    image.setSize(viewPortSize._1,viewPortSize._2)
    image.setPosition(0,0)
    image
  override def drawables: Seq[Drawable] = Seq()
