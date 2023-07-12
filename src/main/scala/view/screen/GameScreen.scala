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
import view.Sprites.getPlayerSprite
import view.screen.Drawable
import view.PlayerProcessor

class GameScreen(world: World) extends BasicScreen:

  override def show(): Unit = 
    super.show()
    Gdx.input.setInputProcessor(PlayerProcessor(world))
  
  override def viewport: Viewport = FitViewport(1000,1000)
  val speed: (Double, Double) = (viewport.getWorldWidth/10, viewport.getWorldHeight/10)

  override def drawables: Seq[screen.Drawable] =
    world.visibleEntities.map(o => Drawable(getPlayerSprite(o),o.position.x.toFloat,o.position.y.toFloat,o.height,o.width)).toList
/*
   def handleInput(): Unit =
     var x = world.player.position.x
     var y = world.player.position.y

     if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
       x += speed._1
     } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
       x += speed._1
     } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
       y += speed._2
     } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
       y -= speed._2
     }

    if (isValidCell(newPlayerPosition.x, newPlayerPosition.y))
       newPosition = newPlayerPosition // Aggiorna la variabile newPosition solo se la nuova posizione è valida

   def isValidCell(x: Int, y: Int): Boolean =
     val gridX = x / cellSize
     val gridY = y / cellSize

     gridX >= 0 && gridX < gridWidth && gridY >= 0 && gridY < gridHeight*/


  /*val viewport: Viewport = PokemonArena.viewport
  private lazy val stage: Stage = Stage(viewport)
  private lazy val camera = viewport.getCamera
  private lazy val batch: SpriteBatch = SpriteBatch()*/

  /*val player: Player = Player("op4", 0, 0, 100, 100, pokemonChosed)
  val op1: Trainer = Trainer("op0", 150, 150, 100, 100, List.empty)
  val op2: Trainer = Trainer("op1", 300, 300, 100, 100, List.empty)
  val op3: Trainer = Trainer("op2", 500, 500, 100, 100, List.empty)
  val op4: Trainer = Trainer("op3", 900, 900, 100, 100, List.empty)
  val backgroundMap: Map = Map("assets/rooms/map_0.png", 0, 0, viewport.getWorldWidth.toInt, viewport.getWorldHeight.toInt, List(player, op1, op2, op3, op4))

  val cellSize = 100
  val gridWidth = backgroundMap.bounds.width / cellSize
  val gridHeight = backgroundMap.bounds.height / cellSize
  val speed = 100

  var newPosition: Position = player.position

  override def render(delta: Float): Unit =
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    //batch.setProjectionMatrix(camera.combined)
    batch.begin()

    batch.draw(backgroundMap.background, backgroundMap.bounds.x, backgroundMap.bounds.y, backgroundMap.bounds.width, backgroundMap.bounds.height)
    for el <- backgroundMap.entities do
      el match
        case p: Player =>
          handleInput(p)
          val updatedPlayer = p.updatePosition(newPosition)
          backgroundMap.updateEntities(newPosition)
          batch.draw(Texture(getPlayerSprite(updatedPlayer)), updatedPlayer.position.x, updatedPlayer.position.y, updatedPlayer.width, updatedPlayer.height)
        case _ =>
          batch.draw(Texture(getPlayerSprite(el)), el.position.x, el.position.y, el.width, el.height)

    batch.end()
    stage.draw()
    stage.act(delta)

  def handleInput(p: Player): Unit =
    var playerX = p.position.x
    var playerY = p.position.y

    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      playerX -= speed
    } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      playerX += speed
    } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      playerY += speed
    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      playerY -= speed
    }

    val newPlayerPosition = Position(playerX, playerY) // Calcola la nuova posizione del giocatore

    if (isValidCell(newPlayerPosition.x, newPlayerPosition.y))
      newPosition = newPlayerPosition // Aggiorna la variabile newPosition solo se la nuova posizione è valida


  def isValidCell(x: Int, y: Int): Boolean =
    val gridX = x/cellSize
    val gridY = y/cellSize

    gridX >= 0 && gridX < gridWidth && gridY >= 0 && gridY < gridHeight

  override def resize(width: Int, height: Int): Unit =
    viewport.update(width, height)
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0)
    camera.update()

 */
