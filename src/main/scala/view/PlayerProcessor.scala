package view
import com.badlogic.gdx.InputAdapter
import model.entities.World
import com.badlogic.gdx.Input.Keys
import model.entities.World.Position
import com.badlogic.gdx

/**
 * Class that controls the movement of the Player
 * @param world contain the information's about the [[Player]] and the current [[Level]]
 */
class PlayerProcessor(world: World) extends InputAdapter:
  private var x = world.player.position.x
  private var y = world.player.position.y
  private val playerSpeed = world.level.playerSpeed

  override def keyDown(keycode: Int): Boolean =
    keycode match
      case Keys.RIGHT => {x = playerSpeed; y = 0; updateSpritePlayer("right")}
      case Keys.LEFT =>  {x = -playerSpeed; y = 0; updateSpritePlayer("left")}
      case Keys.UP => {y = playerSpeed; x = 0; updateSpritePlayer("up")}
      case Keys.DOWN => {y = -playerSpeed; x = 0; updateSpritePlayer("player")}
      case _ => return false
    canMove

  private def canMove: Boolean =
    val newX = world.player.position.x+x
    val newY = world.player.position.y+y
    val withinXBounds = newX >= 0 && newX <= world.level.gridDimension-1
    val withinYBounds = newY >= 0 && newY <= world.level.gridDimension-1

    if (withinXBounds && withinYBounds)
      updatePlayerPosition(newX, newY)

    withinXBounds && withinYBounds

  private def updatePlayerPosition(newX: Int, newY: Int): Unit =
    world.player = world.player withPosition Position(newX, newY)

  private def updateSpritePlayer(direction: String): Unit = world.player = world.player movesTo direction