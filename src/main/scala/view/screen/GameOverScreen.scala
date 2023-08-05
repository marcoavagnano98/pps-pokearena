package view.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, Table, TextButton}
import com.badlogic.gdx.utils.viewport.{ScreenViewport, Viewport}
import controller.events.ResetGame
import model.entities.World
import model.entities.pokemon.Pokemon
import pokearena.PokeArena
import util.Stats
import view.battle.DialogueBox
import view.GdxUtil.onTouchDown
import view.Sprites.getPokemonSprite
import model.entities.{Door, Item, Trainer}
import model.entities.GameStatus.*

class GameOverScreen(world: World, stats: Stats) extends BasicScreen:

  override def drawables: Seq[Drawable] = Seq(Drawable("assets/background/endGame.png", 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight))

  override def actors: Seq[Actor] =
    val rootTable: Table = Table()
    rootTable.setFillParent(true)
    val screenWidth: Float = Gdx.graphics.getWidth
    val screenHeight: Float = Gdx.graphics.getHeight
    val contentWidth: Float = screenWidth
    val contentHeight: Float = screenHeight
    rootTable.setSize(contentWidth, contentHeight)
    rootTable.setPosition((screenWidth - contentWidth)/2.0f, (screenHeight - contentHeight)/2.0f)

    val buttonStart: TextButton = new TextButton("NEW GAME", skin)
    buttonStart.onTouchDown(sendEvent(ResetGame()))

    import view.battle.DialogueBox
    createAndAddInfobox("You reached Level ", world.currentLevel, skin)
    stats.storedData.foreach((item, counter) => createAndAddInfobox(item, counter, skin))

    for (i <- world.player.pokemonTeam)
      createAndAddInfobox(i.name + " was part of your Team with total stats of ", i.bst, skin)

    def createAndAddInfobox(item: String, counter: Int, skin: Skin): Unit =
      val box = DialogueBox(Seq(mapper(item, counter)), skin)
      rootTable.add(box).height(50).width(500).padTop(20)
      rootTable.row()

    rootTable.row()
    rootTable.add(buttonStart).width(200).height(70).padTop(20)
    Seq(rootTable)

  private def mapper(item: String, counter: Int): String =
    item match
      case "trainer" => "You defeated " + counter + " opponents"
      case "item" => "You collected " + counter + " items"
      case "win" => "You defeated the BOSS!!!"
      case "lose" => "I'm sorry, you Lose.. Try Again!"
      case _ => item + counter
      
  override def viewport: Viewport = ScreenViewport()

