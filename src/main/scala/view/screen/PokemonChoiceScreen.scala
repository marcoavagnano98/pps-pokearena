package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Texture}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, InputListener, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Container, Image, Label, Table, TextField}
import com.badlogic.gdx.graphics.g2d.{Batch, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.{ExtendViewport, FillViewport, FitViewport, ScreenViewport, Viewport}
import com.badlogic.gdx.{Screen, ScreenAdapter}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import model.entities.pokemon.{Pokemon, PokemonFactory}
import view.Sprites.getPokemonSprite
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import controller.events.EventDispatcher

class PokemonChoiceScreen(pokemonGenerator: Seq[Pokemon]) extends BasicScreen:

  override def actors: Seq[Actor] =
    val rootTable: Table = Table()
    rootTable.setFillParent(true)
    val sw: Float = Gdx.graphics.getWidth
    val sh: Float = Gdx.graphics.getHeight

    val cw: Float = sw
    val ch: Float = sh

    rootTable.setSize(cw, ch)
    rootTable.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f)

    var pokemonCells: Seq[Map[Image, String]] = Seq()

    var listPokemonChose: Seq[Pokemon] = List()

    val table: Table = Table();
    val pokemonChosenTable: Table = Table()

    for (i <- 1 until 13)
      val pokemonCell = Image(Texture(Gdx.files.internal(getPokemonSprite(pokemonGenerator(i - 1)))))
      pokemonCells = pokemonCells :+ Map(pokemonCell -> i.toString)
      pokemonCell.addListener(new ClickListener() {
        override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
          if pokemonChosenTable.getColumns < 4 then
            pokemonChosenTable.add(pokemonCell).size(100, 100)
            listPokemonChose = listPokemonChose :+ pokemonGenerator(i - 1)
          true
      })

      table.add(pokemonCell).size(100, 100)
      if i % 4 == 0 then
        table.row()

    val mySkin = new Skin(Gdx.files.internal("assets/uiskin.json"))
    val buttonStart: TextButton = new TextButton("START", mySkin)

    buttonStart.addListener(new ClickListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        import controller.events.StartGame
        EventDispatcher.addEvent(StartGame(listPokemonChose))
        true
    })

    rootTable.add(table)
    rootTable.row()
    val blackline: Image = Image(Texture(Gdx.files.internal("assets/blackline.png")))

    rootTable.add(blackline).height(100)
    rootTable.row()
    rootTable.add(pokemonChosenTable)
    rootTable.row()
    rootTable.add(buttonStart).width(200).height(70)
    Seq(rootTable)

  override def viewport: Viewport = ScreenViewport()
  override def background: Option[TextureRegion] = Some(TextureRegion(Texture("assets/pokemon_grass.png"), 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight));