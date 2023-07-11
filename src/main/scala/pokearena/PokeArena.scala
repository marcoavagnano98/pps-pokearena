package pokearena
import com.badlogic.gdx.{Game, Screen}
import model.entities.pokemon.PokemonFactory
import view.screen.PokemonChoiceScreen

class PokeArena extends Game:
  override def create(): Unit =
    setScreen(PokemonChoiceScreen(PokemonFactory(12)))


