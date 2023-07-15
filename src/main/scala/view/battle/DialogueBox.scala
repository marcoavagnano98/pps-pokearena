package view.battle

import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.{Container, Image, ImageTextButton, Label, Skin, Table, TextField}
import com.badlogic.gdx.utils.Align

class DialogueBox(var text: Seq[String], dSkin: Skin) extends Table:
  val skin: Image = Image(Texture("assets/dialogue-box.png"))
  generateLines
  setBackground(skin.getDrawable)

  def generateLines: Unit =
    for
      textLine <- text
      label =  Label(textLine, dSkin)
    do
      label.setColor(Color.BLACK)
     // label.setAlignment(Align.center)
      add(label)//.align(Align.center)
      row()





