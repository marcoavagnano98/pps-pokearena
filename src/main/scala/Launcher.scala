import com.badlogic.gdx.backends.lwjgl3.*
import pokearena.PokeArena

object Launcher:
  @main
  def main(): Unit =
    val config = Lwjgl3ApplicationConfiguration()
    val size =(1000, 1000)
    config.setTitle("PokeArena")
    config.useVsync(true) // Limits FPS to the refresh rate of the currently active monitor.
    config.setWindowIcon("assets/icon/pokearena.png")
    config.setWindowSizeLimits(size._1, size._2, size._1, size._2);
    config.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode.refreshRate)
   // config.setResizable(System.getProperty("os.arch") != "aarch64")
   // config.setWindowedMode(size._1, size._2)
    Lwjgl3Application(PokeArena, config)
