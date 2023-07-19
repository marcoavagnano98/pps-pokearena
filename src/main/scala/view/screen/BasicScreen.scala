package view.screen

import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont, TextureRegion}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{Color, GL20, Texture}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.utils.viewport.Viewport
import controller.events.EventDispatcher

import scala.language.postfixOps

abstract class BasicScreen extends ScreenAdapter:
  def drawables: Seq[Drawable] = Seq.empty
  def writables: Seq[Writable] = Seq.empty
  def actors: Seq[Actor] = Seq.empty
  def viewport: Viewport
  def background:Option[TextureRegion] = Option.empty
  def updateView() : Unit = {}

  private val stage:Stage = Stage(viewport)
  private lazy val font: BitmapFont = BitmapFont(Gdx.files.internal("assets/fnt_white.fnt"))
  private val sr: ShapeRenderer = ShapeRenderer()

   override def render(delta: Float): Unit =
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    updateView()
    val batch = stage.getBatch
    batch.begin()
    background match
      case Some(b) => batch.draw(b, 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
      case _ =>
    drawables.foreach(d =>
      batch.draw(getTexture(d.path), d.bounds.x, d.bounds.y, d.bounds.width, d.bounds.height)
    )

    writables.foreach(w =>
      scaleFont(w.height)
        font.draw(batch, w.s, w.pos.x, w.pos.y)
    )
    batch.end()
    stage.draw()
    stage.act(delta)

  private def scaleFont(height: Float): Unit =
    font.getData.setScale(height * 2 * font.getScaleY / font.getLineHeight)

  private def getTexture(path:String) = new Texture(Gdx.files.classpath(path))

  final override def resize(width: Int, height: Int): Unit = stage.getViewport.update(width,height,true)

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
   * @see [[Writable]] and [[Rectangle]]
   */
  def apply(path: String, x: Float, y: Float, width: Float, height: Float): Drawable =
    BasicDrawable(path, Rectangle(x, y, width, height))


/**
 * Can be written to screen.
 */
trait Writable:
  /**
   *
   * @return the string to be written.
   */
  def s: String

  /**
   *
   * @return the position (top left) where the string should be displayed.
   */
  def pos: Vector2

  /**
   *
   * @return the line height.
   */
  def height: Float

/**
 * Contains implementation of [[Writable]]
 */
object Writable:
  /**
   *
   * Basic implementation of [[Writable]]
   */
  case class BasicWritable(s: String, pos: Vector2, height: Float) extends Writable

  /**
   *
   * @see [[Writable]]
   */
  def apply(s: String, pos: Vector2, height: Float): Writable = BasicWritable(s, pos, height)

  /**
   *
   * @see [[Writable]]
   */
  def apply(path: String, x: Float, y: Float, height: Float): Writable =
    BasicWritable(path, Vector2(x, y), height)

  /**
   *
   * @see [[Writable]]
   */
  def apply(s: String, rec: Rectangle): Writable = BasicWritable(s, Vector2(rec.x, rec.y + rec.height), rec.height)

