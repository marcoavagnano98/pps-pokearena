package view.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.{ScreenViewport, Viewport}
import model.entities.World
import model.entities.pokemon.{Pokemon, PokemonFactory}
import pokearena.PokeArena
import util.Stats
import view.battle.DialogueBox

class GameOverScreen(stats: Stats) extends BasicScreen:

  override def drawables: Seq[Drawable] = Seq(Drawable("assets/endGame.png", 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight))

  override def actors: Seq[Actor] =
    val rootTable: Table = Table()
    rootTable.setFillParent(true)
    val screenWidth: Float = Gdx.graphics.getWidth
    val screenHeight: Float = Gdx.graphics.getHeight
    val contentWidth: Float = screenWidth
    val contentHeight: Float = screenHeight
    rootTable.setSize(contentWidth, contentHeight)
    rootTable.setPosition((screenWidth - contentWidth)/2.0f, (screenHeight - contentHeight)/2.0f)

    val mySkin = new Skin(Gdx.files.internal("assets/uiskin.json"))
    val buttonStart: TextButton = new TextButton("NEW GAME", mySkin)

    buttonStart.addListener(new ClickListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        PokeArena.create()
        true
    })

    import view.battle.DialogueBox

    createAndAddInfobox("You reach Level ", stats.levelRoomReached, mySkin)
    createAndAddInfobox("You defeated ", stats.trainerDefeated+ " Opponents", mySkin)
    if (stats.bossDefeated)
      createAndAddInfobox("You defeated the BOSS!!!", "Awesome", mySkin)
    stats.team.foreach(member => createAndAddInfobox("Was part of your team: ", member.name, mySkin))

    def createAndAddInfobox[A](text: String, el: A, skin: Skin): Unit =
      val box = DialogueBox(Seq(text + el), skin)
      rootTable.add(box).height(50).width(500).padTop(20)
      rootTable.row()

    rootTable.add(buttonStart).width(200).height(70).padTop(20)
    Seq(rootTable)

  override def viewport: Viewport = ScreenViewport()

