package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Texture}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, InputListener, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Container, Image, Label, SelectBox, Table, TextField}
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.{ScreenViewport, Viewport}
import com.badlogic.gdx.{Screen, ScreenAdapter}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import model.entities.pokemon.Pokemon
import view.Sprites.getPokemonSprite
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import controller.events.{EventDispatcher, StartGame}
import controller.events.StartGame
import view.GdxUtil.{onTouchDown, texture}
import view.Sprites

import scala.language.postfixOps
import view.battle.DialogueBox

class PokemonChoiceScreen(pokemonGenerator: Seq[Pokemon]) extends BasicScreen :
  override def viewport: Viewport = ScreenViewport()
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
    val cellSize = 100

    for (i <- pokemonGenerator.indices)
      val pokemonCell = Image(Texture(Gdx.files.internal(getPokemonSprite(pokemonGenerator(i)))))
      pokemonCells = pokemonCells :+ Map(pokemonCell -> i.toString)
      pokemonCell.onTouchDown(
          if pokemonChosenTable.getColumns < 4 then
            pokemonChosenTable.add(pokemonCell).size(cellSize, cellSize)
            listPokemonChose = listPokemonChose :+ pokemonGenerator(i)
      )

      table.add(pokemonCell).size(cellSize, cellSize)
      if (i+1) % 4 == 0 then
        table.row()


    val selectBox = new SelectBox[String](skin)
    selectBox.setItems("low", "medium", "high")
    selectBox.setSelected("low")
    val difficultyTable = Table()
    difficultyTable.add(Label("difficulty: ", skin)).fillX()
    difficultyTable.add(selectBox).fillY()

    val buttonStart: TextButton = new TextButton("START", skin)
    buttonStart.onTouchDown(
      if listPokemonChose.length > 3 then
        sendEvent(StartGame(listPokemonChose, selectBox.getSelectedIndex+1))
    )

    val infoBox = DialogueBox(Seq("Choose 4 Pokemon and then press Start!"), skin)
    rootTable.add(infoBox).height(cellSize).width(500)
    rootTable.row()
    rootTable.add(table)
    rootTable.row()
    val separatorLine: Image = Image(texture(Sprites.separatorLine))
    rootTable.add(separatorLine).height(cellSize)
    rootTable.row()
    rootTable.add(pokemonChosenTable)
    rootTable.row()
    rootTable.add(buttonStart).width(200).height(70)
    rootTable.add(difficultyTable)
    val background = Image(texture(Sprites.background))
    Seq(background,rootTable)

