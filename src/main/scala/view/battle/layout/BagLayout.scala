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
import view.GdxUtil.onTouchDown

class BagLayout(var layoutData: Bag, skin: Skin, boundary: Rectangle, callback: Int => Unit) extends BaseLayout(boundary):
  override type T = Bag
  val box: DialogueBox = DialogueBox(Seq("Select tool"), skin)
  add(box)
  add(scrollableList).fill().minHeight(100)
  setVisible(false)


  private def generateItemList: Seq[(Int, TextField)] =
    for
      i <- layoutData.items.indices
      label = new TextField(layoutData.items(i).name, skin)
    yield (i, {
      label.setAlignment(Align.center)
      label.onTouchDown(callback(i))
      label
    })

  private def scrollableList: ScrollPane =
    val table = Table()
    row()
    for
    ((index, label) <- generateItemList)
      table.add(label).pad(5)
      if index != (layoutData.items.length - 1) then
        table.row()
    val scrollPane: ScrollPane = ScrollPane(table)
    scrollPane.setFadeScrollBars(false)
    scrollPane

  override def updateLayout(updatedBag: Bag): Unit =
    layoutData = updatedBag
    updateActorByIndex(scrollableList, 1)
