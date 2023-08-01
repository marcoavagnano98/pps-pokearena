package view.screen

import com.badlogic.gdx.utils.viewport.{FitViewport, Viewport}
import com.badlogic.gdx.Gdx
import controller.events.{CollisionEvent, OpenDoor, DisplayGameOverScreen, EventDispatcher}
import model.entities.{DoorState, Player, Trainer, VisibleEntity, World, GameStatus}
import view.Sprites.{getEntitySprite, getMapPath}
import view.screen.Drawable
import view.PlayerProcessor

object ViewportUtil:
  val viewportHeight = 100f
  val viewportWidth = 100f

/**
 * Screen of the Game in witch the Player, the Trainers, the Items and the Door are displayed.
 * @param world contain the information's about the Player and the current Level
 */
class GameScreen(world: World) extends BasicScreen:
  private val aspectRatio = ((ViewportUtil.viewportWidth/world.level.gridDimension)*1/10)

  override def viewport: Viewport = FitViewport(ViewportUtil.viewportWidth, ViewportUtil.viewportHeight)

  override def show(): Unit =
    super.show()
    Gdx.input.setInputProcessor(PlayerProcessor(world))

  override def drawables: Seq[Drawable] =
    Drawable(getMapPath(world.level.idLevel), world.level.levelXPos, world.level.levelYPos, ViewportUtil.viewportWidth, ViewportUtil.viewportHeight) +:
      world.visibleEntities.map(e => Drawable(getEntitySprite(e),
        e.position.x * ViewportUtil.viewportWidth/world.level.gridDimension,
        e.position.y * ViewportUtil.viewportHeight/world.level.gridDimension,
        (e.height * aspectRatio).toInt,
        (e.width * aspectRatio).toInt))

  override def updateView(): Unit =
    if world.level.opponents.isEmpty && world.level.door.state.equals(DoorState.Close) then
      sendEvent(OpenDoor())

    if world.isGameWon.equals(GameStatus.Win) then
      sendEvent(DisplayGameOverScreen())

    world.checkCollision match
      case Some(e:VisibleEntity) => sendEvent(CollisionEvent(e))
      case _ =>
