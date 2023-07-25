package view.battle.layout

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttribute.Position
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{ImageTextButton, Label, ScrollPane, Skin, Table, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import view.battle.DialogueBox
import model.entities.{Bag, Item, Potion, World}

class BagLayout(var layoutData: Bag, skin: Skin, boundary: Rectangle, callback: Int => Unit) extends BaseLayout[Int](boundary, callback) :
  override type T = Bag
  val box: DialogueBox = DialogueBox(Seq("Seleziona uno strumento di cura"), skin)
  add(box)
  generateScrollableTable
  setVisible(false)


  private def generateItemList: Seq[(Int, TextField)] =
    for
      i <- layoutData.items.indices
      label = new TextField(layoutData.items(i).name, skin)
    yield (i, {
      label.setAlignment(Align.center)
      label.addListener(listener(i))
      label
    })

  private def generateScrollableTable: Unit =
    val table = Table()
    row()
    for
    ((index, label) <- generateItemList)
      table.add(label).pad(5)
      if index != (layoutData.items.length - 1) then
        table.row()
    val scrollPane: ScrollPane = ScrollPane(table)
    scrollPane.setFadeScrollBars(false)
    scrollPane.setDebug(true)
    add(scrollPane).fill().minHeight(100)

  override def update(updatedBag: Bag): Unit =
    layoutData = updatedBag
    cleanItemTable
    generateScrollableTable

  private def cleanItemTable: Unit =
    removeActor(getCells.items(1).getActor)
    getCells.removeIndex(1)
