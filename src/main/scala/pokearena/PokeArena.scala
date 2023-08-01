package pokearena
import com.badlogic.gdx.{Game, Gdx, Screen}
import controller.events.EventDispatcher
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}
import controller.{BattleController, GameController}
import model.entities.{Player, Trainer}
import model.battle.Battle
import model.entities.World.Position
import model.entities.generator.PokemonGenerator

object PokeArena extends Game:

  /**
   * Set the game screen to the initial screen
   */
  override def create(): Unit =
    setScreen(PokemonChoiceScreen(PokemonGenerator(12)))

  /**
   * Change screen to one using the given behavior.
   */
  def changeScreen(behavior: BasicScreen): Unit =
    Gdx.app.postRunnable(() =>
      setScreen(behavior)
    )