package view.screen

import com.badlogic.gdx.graphics.{Color, GL20, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch, TextureRegion}
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.ui.{HorizontalGroup, Image, Label, Skin, Table, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage, utils}
import com.badlogic.gdx.utils.viewport.{ScreenViewport, Viewport}
import model.entities.Entity
import com.badlogic.gdx.scenes.scene2d.utils.{Drawable, TextureRegionDrawable}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.utils.{Align, Scaling}
import model.battle.Battle
import view.Sprites


case class BattleScreen(var model: Battle) extends BasicScreen:

  override def viewport = new ScreenViewport()

  def renderBattle(model: Battle): Unit =  this.model = model

  private val shapeRenderer = new ShapeRenderer()
  extension(sr: ShapeRenderer)
    def roundedRect(x: Float,y:Float,width: Float, height: Float, radius: Float): Unit =
      sr.rect(x + radius, y + radius, width - 2*radius, height - 2*radius)
      sr.rect(x + radius, y, width - 2*radius, radius)
      sr.rect(x + width - radius, y + radius, radius, height - 2*radius);
      sr.rect(x + radius, y + height - radius, width - 2*radius, radius);
      sr.rect(x, y + radius, radius, height - 2*radius);

      // Four arches, clockwise too
      sr.arc(x + radius, y + radius, radius, 180f, 90f);
      sr.arc(x + width - radius, y + radius, radius, 270f, 90f);
      sr.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
      sr.arc(x + radius, y + height - radius, radius, 90f, 90f);


  val skin: Skin = new Skin(Gdx.files.internal("assets/uiskin.json"))
  val image1 = new Image(new TextureRegion(new Texture(Sprites.getSpritePokemonId("1"))))
  val region = new TextureRegion(new Texture(Sprites.getSpritePokemonId("4")))
  region.flip(true, false)
  val image2 = new Image(region)
  import com.badlogic.gdx.Gdx

  var table: Table = Table()
  //table.defaults().pad(10F)
  table.setFillParent(true)

  var label: Label = Label("PLAYER NAME", skin)

  label.setAlignment(Align.center);

  var first_table: Table = Table()
  image1.setScaling(Scaling.fit)
  image2.setScaling(Scaling.fit)

  first_table.add(image1)

  val second_table: Table = Table()
  //second_table.setDebug(true)
  second_table.add(image2)
  table.row()
  table.add(first_table).pad(150)
  table.add(second_table).pad(150)
  var third_table: Table = Table()
  third_table.defaults().pad(10F)
  val buttonStart = new TextButton("First Move", skin)

  third_table.add(buttonStart).expand()
  third_table.add(Label("SECOND BUTTON", skin))
  third_table.row()
  third_table.add(Label("THIRD BUTTON", skin))
  third_table.add(Label("FOURTH BUTTON", skin))
  table.row()
  table.add(third_table)
  //stage.addActor(table)
  val group: HorizontalGroup = HorizontalGroup()

  override def actors: Seq[Actor] = Seq(table)
  //stage.setDebugAll(true)
/*
  override def render(delta: Float): Unit =
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    batch.begin()
    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix);
    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.setColor(Color.WHITE)

    shapeRenderer.roundedRect(150,100,250,50,10)

    stage.draw()
    stage.act(delta)
    batch.setProjectionMatrix(camera.combined)

    batch.end()
    shapeRenderer.end()*/