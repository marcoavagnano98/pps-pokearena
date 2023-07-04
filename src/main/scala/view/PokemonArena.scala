package view

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.{FitViewport, Viewport}
import com.badlogic.gdx.{ApplicationAdapter, Game, Gdx}
import view.screen.PokemonChoiceScreen
import model.entities.*
import util.Screen.ScreenBehavior
import util.Screen.BasicScreen.given_Conversion_ScreenBehavior_ScreenAdapter

object PokemonArena extends Game:
  val viewport: Viewport = FitViewport(10, 10)

  /**
   *
   * @param behavior change the screen using the given behaviour
   */
  def changeScreen(behavior: ScreenBehavior): Unit = Gdx.app.postRunnable(() => setScreen(behavior))

  /**
   * TODO
   * @param stats change the screen to a post-game summary utilizing the provided information.
   */
  def endGame(stats: ScreenBehavior): Unit = ???

  override def create(): Unit = {
    //changeScreen(GameScreen())
    //changeScreen(PokemonChoiceScreen())
  }

  override def dispose(): Unit = {
    super.dispose()
  }
