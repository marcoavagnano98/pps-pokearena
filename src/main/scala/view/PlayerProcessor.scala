package view
import com.badlogic.gdx.InputAdapter
import model.entities.World
import com.badlogic.gdx.Input.Keys
import model.entities.World.Position
import com.badlogic.gdx

class PlayerProcessor(world: World) extends InputAdapter:
  private var x = world.player.position.x
  private var y = world.player.position.y
  private val playerSpeed = world.playerSpeed

  private def updatePlayerPosition(newX: Double, newY: Double): Unit =
    world.player = world.player withPosition Position(newX, newY)
    world.checkCollision

  private def updateSpritePlayer(direction:String): Unit = world.player = world.player movesTo direction

  override def keyDown(keycode: Int): Boolean =
    keycode match
      case Keys.RIGHT => {x = playerSpeed; y = 0; updateSpritePlayer("right")}
      case Keys.LEFT =>  {x = -playerSpeed; y = 0; updateSpritePlayer("left")}
      case Keys.UP => {y = playerSpeed; x = 0; updateSpritePlayer("up")}
      case Keys.DOWN => {y = -playerSpeed; x = 0; updateSpritePlayer("player")}
      case _ => return true
    canMove
    true

  private def canMove: Unit =
    val newX = world.player.position.x+x
    val newY = world.player.position.y+y
    val withinXBounds = newX >= 0 && newX <= world.gridWidth-1
    val withinYBounds = newY >= 0 && newY <= world.gridHeight-1

    if (withinXBounds && withinYBounds)
      updatePlayerPosition(newX, newY)

  