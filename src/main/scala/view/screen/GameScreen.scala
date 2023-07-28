package view.screen

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.utils.viewport.{ExtendViewport, FitViewport, Viewport}
import com.badlogic.gdx.{Gdx, Input, ScreenAdapter}
import controller.events.{EndGame, CollisionEvent, EventDispatcher}
import model.entities.{DoorState, Level, Player, Trainer, VisibleEntity, World}
import model.entities.pokemon.*
import view.screen
import view.Sprites.{getEntitySprite, getMapPath}
import view.screen.Drawable
import view.PlayerProcessor

object ViewportUtil:
  val viewportHeight: Float = 100
  val viewportWidth: Float = 100

class GameScreen(world: World) extends BasicScreen:

  override def viewport: Viewport = FitViewport(ViewportUtil.viewportWidth, ViewportUtil.viewportHeight)

  /*override def actors: Seq[Actor] =
    import com.badlogic.gdx.scenes.scene2d.ui.Skin
    import view.battle.DialogueBox
    val mySkin = new Skin(Gdx.files.internal("assets/uiskin.json"))
    val infoBox = DialogueBox(Seq("Choose 4 Pokemon and then press Start!"), mySkin)
    infoBox.setHeight(20)
    infoBox.setWidth(20)
    infoBox.setPosition(5,9)
    Seq(infoBox)*/

  override def show(): Unit =
    super.show()
    Gdx.input.setInputProcessor(PlayerProcessor(world))

  override def drawables: Seq[Drawable] =
    Drawable(getMapPath(world.level.background), world.level.levelXPos, world.level.levelYPos, ViewportUtil.viewportWidth, ViewportUtil.viewportHeight) +:
      world.visibleEntities.map(e => Drawable(getEntitySprite(e),
        e.position.x.toFloat * ViewportUtil.viewportWidth/world.level.gridWidth,
        e.position.y.toFloat * ViewportUtil.viewportHeight/world.level.gridHeight,
        e.height,
        e.width))

  override def updateView(): Unit =
    if world.gameEnded then
      sendEvent(EndGame())

    world.checkCollision match
      case Some(e:VisibleEntity) => sendEvent(CollisionEvent(e))
      case _ =>
