import com.badlogic.gdx.backends.lwjgl3.*
import view.PokemonArena
import pokearena.PokeArena


object Launcher:
  @main
  def main(): Unit =
    val config = Lwjgl3ApplicationConfiguration()
    config.setTitle("PokeArena")
    config.useVsync(true) // Limits FPS to the refresh rate of the currently active monitor.
    config.setWindowIcon("assets/icon/pokearena.png")
    config.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode.refreshRate)
    config.setResizable(System.getProperty("os.arch") != "aarch64")
    config.setWindowedMode(1000, 1000)
    Lwjgl3Application(PokemonArena, config)
