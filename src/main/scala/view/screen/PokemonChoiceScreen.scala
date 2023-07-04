package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Texture}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, InputListener, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Container, Image, Label, Table, TextField}
import com.badlogic.gdx.graphics.g2d.{Batch, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.{ExtendViewport, FillViewport, FitViewport, ScreenViewport}
import com.badlogic.gdx.{Screen, ScreenAdapter}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import model.entities.pokemon.{Pokemon, PokemonFactory}
import view.Sprites.getPokemonSprite
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class PokemonChoiceScreen extends ScreenAdapter:

  //private val stage: Stage = Stage(ExtendViewport(16f,9f,true))
  //private val stage: Stage = Stage(FitViewport(12,9,true))

  private val stage: Stage = Stage(ScreenViewport())

  val rootTable: Table = Table()
  rootTable.setFillParent(true)
  val sw: Float = Gdx.graphics.getWidth
  val sh: Float = Gdx.graphics.getHeight

  val cw: Float = sw
  val ch: Float = sh

  rootTable.setSize(cw, ch)
  rootTable.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f)

  var pokemonCells: Seq[Map[Image,String]] = Seq()

  val pokemonGenerator: Seq[Pokemon] = PokemonFactory(12)
  var listPokemonChose :Seq[Pokemon] = List()


  var table: Table = Table();
  val pokemonChosenTable: Table = Table()

  for (i <- 1 until 13)
    //val pokemonCell = PokemonChoiceCell(getPokemonSprite(pokemonGenerator(i-1)))
    val pokemonCell = Image(Texture(Gdx.files.internal(getPokemonSprite(pokemonGenerator(i-1)))))
    pokemonCells = pokemonCells :+ Map(pokemonCell -> i.toString)
    pokemonCell.addListener(new ClickListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        if pokemonChosenTable.getColumns < 4 then
          pokemonChosenTable.add(pokemonCell).size(100,100)
          listPokemonChose = listPokemonChose :+ pokemonGenerator(i-1)
        true
    })

    table.add(pokemonCell).size(100,100)
    if i % 4 == 0 then
      table.row()

  val mySkin = new Skin(Gdx.files.internal("assets/uiskin.json"))
  val buttonStart: Button = new TextButton("Start", mySkin)

  rootTable.add(table)
  rootTable.row()
  val blackline: Image = Image(Texture(Gdx.files.internal("assets/blackline.png")))
  rootTable.add(blackline).height(100)
  rootTable.row()
  rootTable.add(pokemonChosenTable)
  rootTable.row()
  rootTable.add(buttonStart).width(200).height(70)
  stage.addActor(rootTable)
  //stage.setDebugAll(true)

  val backgroundTexture: TextureRegion = TextureRegion(Texture("assets/pokemon_grass.png"), 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight);
  val sr: ShapeRenderer = ShapeRenderer()
  sr.begin(ShapeRenderer.ShapeType.Line)
  sr.setColor(Color.BLACK)


  override def show(): Unit =
    Gdx.input.setInputProcessor(stage)

  override def render(delta: Float): Unit =
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    stage.getBatch.begin()
    stage.getBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    stage.getBatch.end()
    stage.draw()
    stage.act(delta)

  import com.badlogic.gdx.scenes.scene2d.Event

  override def dispose(): Unit = stage.dispose()

  override def resize(width: Int, height: Int): Unit =
    stage.getViewport.update(width,height,true)




case class Drawable(path:String) extends com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable:
  override def draw(batch: Batch, x: Float, y: Float, width: Float, height: Float): Unit =
    batch.draw(Texture(path),x,y,width,height)

trait Writable

import com.badlogic.gdx.utils.viewport.Viewport

abstract class BasicScreen extends ScreenAdapter:
  def drawable: Seq[Drawable] = Seq.empty
  def writable: Seq[Writable] = Seq.empty
  def actors: Seq[Actor] = Seq.empty
  def viewport: Viewport
  private val stage:Stage = Stage(viewport)

  final override def render(delta: Float): Unit =
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    //drawable.foreach()

    stage.draw()
    stage.act(delta)


  final override def resize(width: Int, height: Int): Unit = stage.getViewport.update(width,height,true)

  final override def show(): Unit =
    stage.clear()
    actors.foreach(stage.addActor)
    Gdx.input.setInputProcessor(stage)

  final override def dispose(): Unit =
    stage.dispose()
