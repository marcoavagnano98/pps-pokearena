package util

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Actor, EventListener, InputEvent, Stage}
import com.badlogic.gdx.utils.viewport.Viewport

trait Drawable:
  /**
   *
   * @return file path of the image to be drawn.
   */
  def path: String

  /**
   *
   * @return the Rectangle where the texture will be drawn into.
   */
  def bounds: Rectangle

/**
 * Contains implementation of [[Drawable]]
 */
object Drawable:
  /**
   *
   * @see [[Rectangle]]
   */
  def apply(path: String, x: Float, y: Float, width: Float, height: Float): Drawable =
    DrawableImpl(path, Rectangle(x, y, width, height))

  /**
   *
   * implementation of [[Drawable]]
   */
  private case class DrawableImpl(path: String, bounds: Rectangle) extends Drawable


object Screen:
  trait ScreenBehavior:
    /**
     *
     * @return the [[Drawable]]s that should be rendered
     */
    def drawables: Seq[Drawable] = Seq.empty

    /**
     *
     * @return [[Actor]]s on the screen, called on screen startup.
     */
    def actors: Seq[Actor] = Seq.empty

    /**
     *
     * @return get the [[Viewport]].
     */
    def viewport: Viewport

  object BasicScreen:
    given Conversion[ScreenBehavior, ScreenAdapter] = BasicScreen(_)

    case class BasicScreen(behavior: ScreenBehavior) extends ScreenAdapter:
      private val camera = behavior.viewport.getCamera
      private lazy val stage = new Stage(behavior.viewport)
      private lazy val batch: SpriteBatch = SpriteBatch()

      override def render(delta: Float): Unit =
        Gdx.gl.glClearColor(0, 0, 0, 1)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.setProjectionMatrix(camera.combined)
        batch.begin()

        behavior.drawables.foreach(el => batch.draw(Texture(el.path), el.bounds.x, el.bounds.y, el.bounds.width, el.bounds.height))

        batch.end()
        stage.draw()
        stage.act(delta)

      override def show(): Unit =
        //font.setUseIntegerPositions(false)
        stage.clear()
        behavior.actors.foreach(stage.addActor)
        Gdx.input.setInputProcessor(stage)

      override def resize(width: Int, height: Int): Unit =
        behavior.viewport.update(width, height)
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0)
        camera.update()