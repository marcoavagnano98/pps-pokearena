package view
import com.badlogic.gdx.InputAdapter
import model.entities.World
import com.badlogic.gdx.Input.Keys
import model.entities.World.Position
import com.badlogic.gdx

class PlayerProcessor(world: World) extends InputAdapter:

  private var x = 0
  private var y = 0
  private val playerSpeed = 2
  private def updatePlayerPosition(): Unit =
    world.player = world.player withPosition Position(world.player.position.x+x,world.player.position.y+y)


  override def keyDown(keycode: Int): Boolean =
    keycode match
      case Keys.RIGHT => x = playerSpeed;
      case Keys.LEFT =>  x = -playerSpeed;
      case Keys.UP => y = playerSpeed;
      case Keys.DOWN => y = -playerSpeed;
      case _ =>
    updatePlayerPosition()
    true