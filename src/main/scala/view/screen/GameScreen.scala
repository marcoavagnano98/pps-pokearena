package view.screen

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.utils.viewport.{ExtendViewport, FitViewport, Viewport}
import com.badlogic.gdx.{Gdx, Input, ScreenAdapter}
import controller.events.EventDispatcher
import model.entities.{Map, Player, Trainer, VisibleEntity, World}
import model.entities.pokemon.*
import util.Screen.ScreenBehavior
import view.screen
import view.Sprites.{getEntitySprite, getMapPath}
import view.screen.Drawable
import view.PlayerProcessor
import controller.events.CollisionEvent

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
    Drawable(world.gameMap.background, world.gameMap.bounds.x, world.gameMap.bounds.y, ViewportUtil.viewportWidth, ViewportUtil.viewportHeight) +:
      world.visibleEntities.map(o => Drawable(getEntitySprite(o),
        o.position.x.toFloat * ViewportUtil.viewportWidth/world.gridWidth,
        o.position.y.toFloat * ViewportUtil.viewportHeight/world.gridHeight,
        o.height,
        o.width))

  override def updateView(): Unit =
    world.checkCollision match
      case Some(e:VisibleEntity) => sendEvent(CollisionEvent(e))
      case _ =>