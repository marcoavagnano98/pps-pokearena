package view

import model.entities.{Map, Opponent, Trainer}
import util.Drawable
import com.badlogic.gdx.utils.viewport.Viewport
import util.Screen.ScreenBehavior

class GameScreen extends ScreenBehavior:
  val gameScreen: Drawable = Map("assets/rooms/5.png", 0, 0, 10, 10)
  val trainer: Drawable = Trainer("assets/trainers/brad.png", 0, 0, 1.5, 1.5)
  val op0: Drawable = Opponent("assets/trainers/opponents/op0.png", 2, 2, 1.5, 1.5, List.empty)
  val op1: Drawable = Opponent("assets/trainers/opponents/op1.png", 3, 3, 1.5, 1.5, List.empty)
  val op2: Drawable = Opponent("assets/trainers/opponents/op2.png", 5, 5, 1.5, 1.5, List.empty)
  val op3: Drawable = Opponent("assets/trainers/opponents/op3.png", 8, 8, 1.5, 1.5, List.empty)

  override def drawables: Seq[Drawable] =
    Seq(gameScreen, trainer, op0, op1, op2, op3)

  override def viewport: Viewport = PokemonArena.viewport
