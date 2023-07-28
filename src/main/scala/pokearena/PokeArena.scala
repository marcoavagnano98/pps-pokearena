package pokearena
import com.badlogic.gdx.{Game, Gdx, Screen}
import controller.events.EventDispatcher
import model.entities.pokemon.PokemonFactory
import view.screen.{BasicScreen, BattleScreen, PokemonChoiceScreen}
import controller.{BattleController, GameController}
import model.entities.{Player, Trainer}
import model.battle.Battle
import model.entities.World.Position

object PokeArena extends Game:
  override def create(): Unit =
    setScreen(PokemonChoiceScreen(PokemonFactory(12)))
  /**
   * Change screen to one using the given behavior.
   */
  def changeScreen(behavior: BasicScreen): Unit =
    Gdx.app.postRunnable(() =>
      setScreen(behavior)
    )

  override def render(): Unit =
    super.render()
