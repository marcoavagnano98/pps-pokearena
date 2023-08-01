package view.battle.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Touchable}
import com.badlogic.gdx.scenes.scene2d.ui.{Cell, ImageTextButton, Skin, Table}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import model.entities.pokemon.{Move, Pokemon}
import view.battle.DialogueBox
import LayoutVisibility.*
import view.GdxUtil.onTouchDown

import scala.collection.mutable
import scala.io.Source

class FightLayout(var layoutData: Pokemon, skin: Skin, boundary: Rectangle, callback: Int => Unit) extends BaseLayout(boundary) :
  override type T = Pokemon

  add(DialogueBox(Seq("Scegli una mossa"), skin)).colspan(2)
  row()
  for (elem <- movesButtons)
    add(elem._2).fill().pad(10)
    if (elem._1 + 1) % 2 == 0 then
      row()
  setVisible(NotVisible.value)

  private def buttonString(move: Move): String =
   move.name + "\n" + move.damage + " dmg | " + move.powerPoint + "PP | " + move.elementType.elemType

  def movesButtons: Seq[(Int, ImageTextButton)] =
    for
      i <- layoutData.moves.indices
      b = ImageTextButton(buttonString(layoutData.moves(i)), skin)
      checkedButton = {
        b.onTouchDown(callback(i)); checkPP(b, layoutData.moves(i))
      }
    yield (i, checkedButton)

  def checkPP(button: ImageTextButton, move: Move): ImageTextButton =
    if move.powerPoint <= 0 then
      button.setTouchable(Touchable.disabled)
    else
      button.setTouchable(Touchable.enabled)
    button

  override def updateLayout(newLayoutInfo: Pokemon): Unit =
    layoutData = newLayoutInfo
    for
      (index, button) <- movesButtons
      cell = getCells.items(index + 1)
    do
      cell.getActor match
        case _: ImageTextButton => cell.setActor(button)
        case _ =>