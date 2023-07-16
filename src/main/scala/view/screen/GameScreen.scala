package view.screen

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.{ExtendViewport, FitViewport, Viewport}
import com.badlogic.gdx.{Gdx, Input, ScreenAdapter}
import model.entities.World
import model.entities.pokemon.*
import model.entities.{Map, Player, Trainer}
import util.Screen.ScreenBehavior
import view.screen
import view.Sprites.{getMapSprite, getPlayerSprite}
import view.screen.Drawable
import view.PlayerProcessor

class GameScreen(world: World) extends BasicScreen:
  override def viewport: Viewport = FitViewport(1000,1000)

  override def show(): Unit = 
    super.show()
    Gdx.input.setInputProcessor(PlayerProcessor(world))

  override def drawables: Seq[Drawable] =
    Drawable(world.gameMap.background, world.gameMap.bounds.x, world.gameMap.bounds.y, world.gameMap.bounds.width, world.gameMap.bounds.height) +:
    world.visibleEntities.map(o => Drawable(getPlayerSprite(o), o.position.x.toFloat, o.position.y.toFloat, o.height, o.width))
