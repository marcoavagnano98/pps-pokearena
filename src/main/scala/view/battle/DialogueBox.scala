package view.battle

import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.{Container, Image, ImageTextButton, Label, Skin, Table, TextField}
import com.badlogic.gdx.utils.Align

case class DialogueBox(text: String, dSkin: Skin) extends Table:
  val skin: Image = Image(Texture("assets/dialogue-box.png"))
  val label = new Label(text, dSkin)
  label.setColor(Color.BLACK)
  label.setAlignment(Align.center)
  add(label).align(Align.center)
  setBackground(skin.getDrawable)




