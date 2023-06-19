import com.badlogic.gdx.backends.lwjgl3.*
import view.Game



object Launcher:
  @main
  def main(): Unit =
    val config = Lwjgl3ApplicationConfiguration()
    config.setTitle("PokeArena")
    config.setResizable(System.getProperty("os.arch") != "aarch64")
    config.setWindowedMode(960, 540)
    Lwjgl3Application(Game, config)
