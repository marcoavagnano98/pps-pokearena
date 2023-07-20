package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch, TextureRegion}
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.ui.{Container, HorizontalGroup, Image, ImageTextButton, Label, ProgressBar, Skin, Slider, Table, TextButton, TextField, VerticalGroup, Widget}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage, utils}
import com.badlogic.gdx.utils.viewport.{ScreenViewport, Viewport}
import model.entities.{Entity, ItemFactory, ItemId, Potion}
import com.badlogic.gdx.scenes.scene2d.utils.{ClickListener, TextureRegionDrawable}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.utils.{Align, Scaling, Timer}
import model.battle.{Battle, BattleTurnEvent, Turn}
import view.{Sprites, screen}
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import controller.events.{EndBattle, EventDispatcher, OptionChosen}
import model.battle.BattleTurnEvent.*
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

  override def viewport = new ScreenViewport()

  val battleMenuLayout: BattleMenuBaseLayout = BattleMenuBaseLayout(Seq("Cosa deve fare " + battle.player.pokemonTeam.head.name), skin, battleMenuRegion, menuLayoutAction)
  val fightLayout: FightLayout = FightLayout(battle.player.pokemonTeam.head, skin, battleMenuRegion, fightLayoutAction)
  val bagLayout: BagLayout = BagLayout(Seq(ItemFactory(ItemId.Potion)), skin, battleMenuRegion, bagLayoutAction)
  val pPlayerInfoLayout: PokemonInfoLayout = PokemonInfoLayout(battle.player.pokemonTeam.head, skin, Rectangle(pBRegion.x + (pBRegion.width + 50), pBRegion.y, 200, 100))
  val pOpponentLayout: PokemonInfoLayout = PokemonInfoLayout(battle.opponent.pokemonTeam.head, skin, Rectangle(oBRegion.x - (oBRegion.width + 100), oBRegion.y, 200, 100))

  def battleScreenUpdate(turnData:(Turn, Turn)): Unit =
    fightLayout.setVisible(false)
    bagLayout.setVisible(false)
    battleMenuLayout.setVisible(true)
    battleMenuLayout.hideButtonMenu
    battleMenuLayout.update(
      Seq(
        turnData._1.pokemon.name + " " + turnData._1.battleTurnEvent.description,
        turnData._2.pokemon.name + " " + turnData._2.battleTurnEvent.description
      ))
    com.badlogic.gdx.utils.Timer.schedule(new Timer.Task() {
      override def run(): Unit = {
        battle.pokemonInBattle match
          case (Some(playerPokemon), Some(opponentPokemon)) =>
            fightLayout.update(playerPokemon)
            battleMenuLayout.update(Seq("Cosa deve fare " + playerPokemon.name + "?"))
            pPlayerInfoLayout.update(playerPokemon)
            pOpponentLayout.update(opponentPokemon)
            battleMenuLayout.showButtonMenu
          case (Some(_), None) => sendEvent(EndBattle(battle.opponent.id))
          case _ => sendEvent(EndBattle(battle.player.id))
      }
    }, 2)

  private def pBRegion: Rectangle = Rectangle(
    Gdx.graphics.getWidth / 5, Gdx.graphics.getHeight / 2, 150, 150)

  private def oBRegion: Rectangle = Rectangle(
    Gdx.graphics.getWidth - (Gdx.graphics.getWidth / 4),
    Gdx.graphics.getHeight - (Gdx.graphics.getHeight / 4), 150, 150)

  private def battleMenuRegion: Rectangle = Rectangle(
    Gdx.graphics.getWidth / 4, 50, Gdx.graphics.getWidth / 2, 200
  )

  private def menuLayoutAction(option: BattleMenuOption): Unit =
    option match
      case FightOption => fightLayout.setVisible(true)
      case _ => bagLayout.setVisible(true)
    battleMenuLayout.setVisible(false)

  /*
  * We are sure player pokemon is present
  * */
  private def fightLayoutAction(moveIndex: Int): Unit =
    val pokemon = battle.pokemonInBattle._1.get
    val move: Move = pokemon.moves(moveIndex)
    val fightOption: BattleTurnEvent = Attack(move)
    sendEvent(OptionChosen(fightOption))

  private def bagLayoutAction(itemIndex: Int): Unit = {}

  private def pokemonDrawables: Seq[Drawable] =
    battle.pokemonInBattle match
      case (Some(pPokemon), Some(oPokemon)) => Seq(Drawable(Sprites.getBattleSprite(pPokemon.id), pBRegion.x, pBRegion.y, pBRegion.width, pBRegion.height),
        Drawable(Sprites.getBattleSprite(oPokemon.id), oBRegion.x, oBRegion.y, oBRegion.width, oBRegion.height))
      case _ => Seq()

  override def actors: Seq[Actor] =
    Seq(battleMenuLayout,
      fightLayout,
      bagLayout,
      pPlayerInfoLayout,
      pOpponentLayout)

  override def drawables: Seq[Drawable] =
    Seq(Drawable("assets/pokemon_grass.png", 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)) concat pokemonDrawables