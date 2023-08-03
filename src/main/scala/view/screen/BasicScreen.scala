package view.screen

import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont, TextureRegion}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{Color, GL20, Texture}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.utils.viewport.Viewport
import controller.events.EventDispatcher

import scala.language.postfixOps
import view.GdxUtil.MemoHelper.*
import view.GdxUtil.*
import view.Sprites.*

abstract class BasicScreen extends ScreenAdapter with EventDispatcher:
  def drawables: Seq[Drawable] = Seq.empty

  def actors: Seq[Actor] = Seq.empty

  def viewport: Viewport

  def updateView(): Unit = {}

  private val stage: Stage = Stage(viewport)
  private lazy val font: BitmapFont = BitmapFont(Gdx.files.internal("assets/skin/fnt_white.fnt"))
  lazy val skin = new Skin(Gdx.files.internal("assets/skin/uiskin.json"))

  override def render(delta: Float): Unit =
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    updateView()
    val batch = stage.getBatch
    batch.begin()

    drawables.foreach(d =>
      batch.draw(memoizeTexture(d.path), d.bounds.x, d.bounds.y, d.bounds.width, d.bounds.height)
    )

    batch.end()
    stage.draw()
    stage.act(delta)
    dispatch()

  private def scaleFont(height: Float): Unit =
    font.getData.setScale(height * 2 * font.getScaleY / font.getLineHeight)

  private val memoizeTexture: String => Texture =
    memoize(texture)

  final override def resize(width: Int, height: Int): Unit = stage.getViewport.update(width, height, true)

  override def show(): Unit =
    stage.clear()
    actors.foreach(stage.addActor)
    Gdx.input.setInputProcessor(stage)

  final override def dispose(): Unit =
    stage.dispose()

trait Drawable:
  /**
   *
   * @return file path of the image to be drawn.
   */
  def path: String

  /**
   *
   * @return the bounds where the texture will be drawn into.
   */
  def bounds: Rectangle

/**
 * Contains implementation of [[Drawable]]
 */
object Drawable:

  /**
   *
   * Basic implementation of [[Drawable]]
   */
  case class BasicDrawable(path: String, bounds: Rectangle) extends Drawable

  /**
   *
   * @see [[Drawable]]
   */
  def apply(path: String, bounds: Rectangle): Drawable =
    BasicDrawable(path, bounds)

  /**
   *
   * @see [[Drawable]] and [[Rectangle]]
   */
  def apply(path: String, x: Float, y: Float, width: Float, height: Float): Drawable =
    BasicDrawable(path, Rectangle(x, y, width, height))