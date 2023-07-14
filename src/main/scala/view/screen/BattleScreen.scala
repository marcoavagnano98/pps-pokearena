package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch, TextureRegion}
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.ui.{Container, HorizontalGroup, Image, ImageTextButton, Label, ProgressBar, Skin, Slider, Table, TextButton, TextField, VerticalGroup, Widget}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage, utils}
import com.badlogic.gdx.utils.viewport.{ScreenViewport, Viewport}
import model.entities.Entity
import com.badlogic.gdx.scenes.scene2d.utils.{ClickListener, Drawable, TextureRegionDrawable}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.utils.{Align, Scaling}
import model.battle.Battle
import view.{Sprites, screen}
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import controller.events.{EventDispatcher, OptionChosen}
import model.battle.BattleOption.*
import model.entities.pokemon.PokemonFactory
import pokearena.PokeArena
import view.battle.DialogueBox
import view.battle.layout.BattleMenuOption.{FightOption, BagOption}
import view.battle.layout.{BattleMenuLayout, BattleMenuOption, FightLayout}
import view.screen.Drawable


class BattleScreen() extends BasicScreen :
  val skin: Skin = new Skin(Gdx.files.internal("assets/uiskin.json"))
  override def viewport = new ScreenViewport()

  override def background: Option[TextureRegion] = Some(TextureRegion(Texture("assets/pokemon_grass.png"), 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight));

  val battleMenuLayout: BattleMenuLayout = BattleMenuLayout("bulbasaur", skin, battleMenuRegion,menuLayoutAction)
  val fightLayout: FightLayout = FightLayout(PokemonFactory(1).head, skin,battleMenuRegion, fightLayoutAction)
  fightLayout.setVisible(false)

  def updateView: Unit = ???


  private def pBRegion: Rectangle = Rectangle(
    Gdx.graphics.getWidth / 5, Gdx.graphics.getHeight / 2, 150, 150)

  private def oBRegion: Rectangle = Rectangle(
    Gdx.graphics.getWidth - (Gdx.graphics.getWidth / 4),
    Gdx.graphics.getHeight - (Gdx.graphics.getHeight / 4), 150, 150)

  private def battleMenuRegion: Rectangle = Rectangle(
    Gdx.graphics.getWidth / 4, 50, Gdx.graphics.getWidth / 2, 200
  )

  def menuLayoutAction(option: BattleMenuOption): Unit =
    option match
      case FightOption => fightLayout.setVisible(true)
      case _ =>
    battleMenuLayout.setVisible(false)

  private def fightLayoutAction(moveIndex: Int): Unit =
    fightLayout.setVisible(false)
    battleMenuLayout.setVisible(true)
    //EventDispatcher.addEvent(OptionChosen(Attack()))

  override def actors: Seq[Actor] =
    Seq(battleMenuLayout, fightLayout)

  override def drawables: Seq[view.screen.Drawable] =
    Seq(
      view.screen.Drawable(Sprites.getBattleSprite("1"), pBRegion.x, pBRegion.y, pBRegion.width, pBRegion.height),
      view.screen.Drawable(Sprites.getBattleSprite("4"), oBRegion.x, oBRegion.y, oBRegion.width, oBRegion.height))