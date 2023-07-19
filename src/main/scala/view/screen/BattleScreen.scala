package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch, TextureRegion}
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.ui.{Container, HorizontalGroup, Image, ImageTextButton, Label, ProgressBar, Skin, Slider, Table, TextButton, TextField, VerticalGroup, Widget}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage, utils}
import com.badlogic.gdx.utils.viewport.{ScreenViewport, Viewport}
import model.entities.{Entity, ItemFactory, Potion}
import com.badlogic.gdx.scenes.scene2d.utils.{ClickListener, Drawable, TextureRegionDrawable}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.utils.{Align, Scaling, Timer}
import model.battle.{Battle, BattleChoice}
import view.{Sprites, screen}
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import controller.events.{EventDispatcher, OptionChosen}
import model.battle.BattleChoice.*
import model.entities.World.Position
import model.entities.pokemon.{Move, PokemonFactory}
import pokearena.PokeArena
import view.battle.DialogueBox
import view.battle.layout.BattleMenuOption.{BagOption, FightOption}
import view.battle.layout.{BagLayout, BattleMenuLayout, BattleMenuOption, FightLayout, PokemonInfoLayout}
import view.screen.Drawable


class BattleScreen(battle: Battle) extends BasicScreen :
  val skin: Skin = new Skin(Gdx.files.internal("assets/uiskin.json"))
  override def viewport = new ScreenViewport()

  override def background: Option[TextureRegion] = Some(TextureRegion(Texture("assets/pokemon_grass.png"), 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight));

  val battleMenuLayout: BattleMenuLayout = BattleMenuLayout(battle.player.pokemonTeam.head.name, skin, battleMenuRegion,menuLayoutAction)
  val fightLayout: FightLayout = FightLayout(battle.player.pokemonTeam.head, skin,battleMenuRegion, fightLayoutAction)
  fightLayout.setVisible(false)
  val bagLayout: BagLayout =  BagLayout(Seq(ItemFactory("",4))
  bagLayout.setVisible(false)
  val pPlayerInfoLayout: PokemonInfoLayout = PokemonInfoLayout(battle.player.pokemonTeam.head,skin, Rectangle(pBRegion.x + (pBRegion.width + 50), pBRegion.y,200,100))
  val pOpponentLayout: PokemonInfoLayout = PokemonInfoLayout(battle.opponent.pokemonTeam.head,skin, Rectangle(oBRegion.x - (oBRegion.width + 100), oBRegion.y,200,100))

  def updateView: Unit =

    com.badlogic.gdx.utils.Timer.schedule(new Timer.Task() {
      override def run(): Unit = {
        battle.pokemonInBattle match
          case (Some(first), Some(second)) =>
            fightLayout.setVisible(false)
            bagLayout.setVisible(false)
            fightLayout.update(first)
            battleMenuLayout.update(first.name)
            pPlayerInfoLayout.update(first)
            pOpponentLayout.update(second)
            battleMenuLayout.setVisible(true)
          case _ =>
      }
    }, 1)

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

  private def fightLayoutAction(moveIndex: Int): Unit =
    val pokemon = battle.pokemonInBattle._1.get
    val move: Move = pokemon.moves(moveIndex)
    val fightOption: BattleChoice = Attack(move)
    EventDispatcher.addEvent(OptionChosen(fightOption))

  private def bagLayoutAction(itemIndex: Int): Unit = {}

  override def actors: Seq[Actor] =
    Seq(battleMenuLayout,
      fightLayout,
      bagLayout,
      pPlayerInfoLayout,
      pOpponentLayout)

  override def drawables: Seq[view.screen.Drawable] =
    val pPlayerId = battle.pokemonInBattle._1.get.id
    val pOpponentId = battle.pokemonInBattle._2.get.id
    Seq(
      view.screen.Drawable(Sprites.getBattleSprite(pPlayerId), pBRegion.x, pBRegion.y, pBRegion.width, pBRegion.height),
      view.screen.Drawable(Sprites.getBattleSprite(pOpponentId), oBRegion.x, oBRegion.y, oBRegion.width, oBRegion.height))