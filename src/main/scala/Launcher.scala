import com.badlogic.gdx.backends.lwjgl3.*
import com.badlogic.gdx.{Game, Gdx, ScreenAdapter}

object Game extends com.badlogic.gdx.Game:
  override def create(): Unit = ???

object Launcher:
  @main
  def main(): Unit =
    val config = Lwjgl3ApplicationConfiguration()
    config.setTitle("PokeArena")
    config.setResizable(System.getProperty("os.arch") != "aarch64")
    config.setWindowedMode(960, 540)
    Lwjgl3Application(Game, config)
