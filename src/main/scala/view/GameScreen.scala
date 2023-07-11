package view

import model.entities.{Map, Trainer}
import util.Drawable
import com.badlogic.gdx.utils.viewport.Viewport
import util.Screen.ScreenBehavior
import view.Sprites.getPlayerSprite

class GameScreen extends ScreenBehavior:
  val gameScreen: Drawable = Map("assets/rooms/5.png", 0, 0, 10, 10)
  //val trainer: Drawable = Trainer("assets/trainers/op4.png", 0, 0, 1.5, 1.5)
  val trainer2: Trainer = Trainer(0, 0, "op1", Seq())
  /*val op0: Drawable = Opponent("assets/trainers/opponents/op0.png", 2, 2, 1.5, 1.5)
  val op1: Drawable = Opponent("assets/trainers/opponents/op1.png", 3, 3, 1.5, 1.5)
  val op2: Drawable = Opponent("assets/trainers/opponents/op2.png", 5, 5, 1.5, 1.5)
  val op3: Drawable = Opponent("assets/trainers/opponents/op3.png", 8, 8, 1.5, 1.5)*/

  override def drawables: Seq[Drawable] =
    Seq(gameScreen, Drawable(getPlayerSprite(trainer2), trainer2.position.x, trainer2.position.y, trainer2.width, trainer2.height))

  override def viewport: Viewport = PokemonArena.viewport
