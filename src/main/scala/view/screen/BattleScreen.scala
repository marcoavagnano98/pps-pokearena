package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch, TextureRegion}
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.ui.{Container, HorizontalGroup, Image, ImageButton, ImageTextButton, Label, ProgressBar, Skin, Slider, Table, TextButton, TextField, VerticalGroup, Widget}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage, Touchable, utils}
import com.badlogic.gdx.utils.viewport.{FitViewport, ScreenViewport, Viewport}
import model.entities.{Entity, ItemFactory, Potion}
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
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
import controller.events.{EventDispatcher, OptionChosen, EndFight}
import model.battle.Status.*
import model.battle.TrainerChoice.*
import model.entities.World.Position
import model.entities.pokemon.Move
import pokearena.PokeArena
import view.battle.DialogueBox
import view.battle.layout.BattleMenuOption.{BagOption, FightOption}
import view.battle.layout.{BagLayout, BattleMenuLayout, BattleMenuOption, FightLayout, PokemonInfoLayout}
import view.screen.Drawable

import scala.language.postfixOps
import view.battle.layout.LayoutVisibility.*
import view.GdxUtil.*


class BattleScreen(battle: Battle) extends BasicScreen :
  private def viewPortSize: (Float, Float) = (1000, 1000)

  override def viewport: FitViewport = FitViewport(viewPortSize._1, viewPortSize._2)

  val battleMenuLayout: BattleMenuLayout = BattleMenuLayout(Seq(menuTitle(battle.player.pokemonTeam.head.name)), skin, battleMenuRegion, menuLayoutAction)
  val fightLayout: FightLayout = FightLayout(battle.player.pokemonTeam.head, skin, battleMenuRegion, fightLayoutAction)
  val bagLayout: BagLayout = BagLayout(battle.player.bag, skin, battleMenuRegion, bagLayoutAction)
  val pPlayerInfoLayout: PokemonInfoLayout = PokemonInfoLayout(battle.player.pokemonTeam.head, skin, Rectangle(pBRegion.x, pBRegion.y, viewPortSize._1 / 2, 100))
  val pOpponentLayout: PokemonInfoLayout = PokemonInfoLayout(battle.opponent.pokemonTeam.head, skin, Rectangle(oBRegion.x, oBRegion.y, viewPortSize._1 / 2, 100))
  showBattleMenu


  def backButton: ImageTextButton =
    val backButton = ImageTextButton("Back", skin)
    backButton.setBounds(battleMenuRegion.x + battleMenuRegion.width + 20, battleMenuRegion.y + (battleMenuRegion.height / 2), 80, 50)
    backButton.onTouchDown(showBattleMenu)
    backButton

  def showBattleMenu: Unit =
    fightLayout.setVisible(NotVisible.value)
    bagLayout.setVisible(NotVisible.value)
    battleMenuLayout.setVisible(Visible.value)

  private def menuTitle(pokemonName: String): String = "Cosa deve fare " + pokemonName + "?"

  def battleScreenUpdate(turnData: Seq[Turn]): Unit =
    showBattleMenu
    battleMenuLayout.setButtonsVisibility(NotVisible)

    battleMenuLayout.updateLayout(
      turnData
        .collect({
          case t if t.performed => t.pokemon.name + " " + t.trainerChoice.description
        })
        ++
        (for
          turn <- turnData
          description <- turn.turnStatus.description
          title = turn.pokemon.name + " " + description
        yield title)

    )
    scheduleDelayedAction(2,
      {
        battle.pokemonInBattle match
          case (Some(playerPokemon), Some(opponentPokemon)) =>
            fightLayout.updateLayout(playerPokemon)
            battleMenuLayout.updateLayout(Seq(menuTitle(playerPokemon.name)))
            bagLayout.updateLayout(battle.player.bag)
            pPlayerInfoLayout.updateLayout(playerPokemon)
            pOpponentLayout.updateLayout(opponentPokemon)
            battleMenuLayout.setButtonsVisibility(Visible)
          case _ =>
        if turnData.exists(_.turnStatus == Defeat) then sendEvent(EndFight())
      }
    )


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
    val image = Image(Texture("assets/background/battle-screen.png"))
    image.setBounds(0, 0, viewPortSize._1, viewPortSize._2)
    image

  override def drawables: Seq[Drawable] = Seq()
