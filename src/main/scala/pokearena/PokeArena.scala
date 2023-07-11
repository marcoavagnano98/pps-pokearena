package pokearena
import com.badlogic.gdx.{Game, Screen}
import controller.events.{EndBattle, EventDispatcher}
import model.entities.pokemon.PokemonFactory
import view.screen.PokemonChoiceScreen


object PokeArena extends Game:
  override def create(): Unit =
    setScreen(PokemonChoiceScreen(PokemonFactory(12)))

  override def render(): Unit =
    super.render()
    EventDispatcher.dispatchAll()