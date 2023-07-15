package pokearena
import com.badlogic.gdx.{Game, Screen,Gdx}
import controller.events.{EndBattle, EventDispatcher}
import model.entities.pokemon.PokemonFactory
import view.screen.{PokemonChoiceScreen, BasicScreen, BattleScreen}
import controller.GameController
object PokeArena extends Game:
  override def create(): Unit =
   // setScreen(PokemonChoiceScreen(PokemonFactory(12)))
    setScreen(BattleScreen())
  /**
   * Change screen to one using the given behavior.
   */
  def changeScreen(behavior: BasicScreen): Unit =
    Gdx.app.postRunnable(() =>
      setScreen(behavior)
    )

  override def render(): Unit =
    super.render()
    EventDispatcher.dispatch()