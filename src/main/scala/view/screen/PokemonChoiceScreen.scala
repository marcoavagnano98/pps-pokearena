package view.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Label, Table, TextField}
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.{Screen, ScreenAdapter}

class PokemonChoiceScreen extends ScreenAdapter:

  private val stage: Stage = Stage(ExtendViewport(16f,9f))

  override def show(): Unit =
    println("Log")
    import com.badlogic.gdx.Gdx
    import com.badlogic.gdx.scenes.scene2d.ui.Skin


    import com.badlogic.gdx.Gdx
    import com.badlogic.gdx.graphics.Texture

    val pokemonImage = new Image(Texture(Gdx.files.internal("sprites/pokedex/001MS.png")))
    import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
    pokemonImage.addListener(new ClickListener() {
      def clicked(event: Nothing, x: Float, y: Float): Unit = {
      System.out.println("You clicked an image...")
      }
    })
    val pokemonImage1= new Image(Texture(Gdx.files.internal("sprites/pokedex/002MS.png")))

    val table = Table();
    stage.addActor(table)
    table.setDebug(true)

    table.setFillParent(true)
    table.center()
    table.add(pokemonImage)
    table.add(new Image(Texture(Gdx.files.internal("sprites/pokedex/001MS.png")))).uniform()
    table.row()
    table.add(new Image(Texture(Gdx.files.internal("sprites/pokedex/002MS.png")))).uniform()
    table.add(new Image(Texture(Gdx.files.internal("sprites/pokedex/002MS.png")))).uniform()

  override def render(delta: Float): Unit =
    stage.act(delta)
    stage.draw()

  override def dispose(): Unit = stage.dispose()

  override def resize(width: Int, height: Int): Unit = stage.getViewport.update(width,height,true)