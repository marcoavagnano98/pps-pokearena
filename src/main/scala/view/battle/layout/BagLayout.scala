package view.battle.layout

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttribute.Position
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{ImageTextButton, Label, ScrollPane, Skin, Table, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import view.battle.DialogueBox
import model.entities.{Item, Potion, World}

class BagLayout(var layoutInfo: Seq[Item], skin: Skin, rect:Rectangle, actionPerformed: Int => Unit) extends Table with Layout[Seq[Item]]:
  val box: DialogueBox = DialogueBox(Seq("Seleziona uno strumento di cura"), skin)
  add(box)
  box.setDebug(true)
  generateScrollableTable
  setSize(rect.width, rect.height)
  setPosition(rect.x, rect.y)

  def itemListener(itemIndex: Int): ClickListener =
    new ClickListener(){
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        super.touchDown(event, x, y, pointer, button)
        actionPerformed(itemIndex)
        true
    }
 
  private def generateItemList: Seq[(Int,TextField)] =
    for
      i <- layoutInfo.indices
      label = new TextField(layoutInfo(i).name, skin)
    yield (i, {
      label.setAlignment(Align.center)
      label.addListener(itemListener(i))
      label
    })

  private def generateScrollableTable: Unit =
    val table = Table()
    table.setDebug(true)
    row()
    for
    ((index, label) <- generateItemList)
      table.add(label).pad(5)
      if index != (layoutInfo.length - 1) then
        table.row()
    val scrollPane: ScrollPane = ScrollPane(table)
    scrollPane.setFadeScrollBars(false)
    scrollPane.setDebug(true)
    add(scrollPane).fill().minHeight(100)

  override def update(newItemList: Seq[Item]): Unit =
    layoutInfo = newItemList
    cleanItemTable
    generateScrollableTable

  private def cleanItemTable: Unit =
    removeActor(getCells.items(1).getActor)
    getCells.removeIndex(1)
