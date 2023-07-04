package pokearena
import com.badlogic.gdx.{Game, Screen}
import view.screen.PokemonChoiceScreen

class PokeArena extends Game:
  override def create(): Unit =
    setScreen(PokemonChoiceScreen())


