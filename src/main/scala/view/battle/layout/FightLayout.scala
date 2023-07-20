package view.battle.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Cell, ImageTextButton, Skin, Table}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import model.entities.pokemon.{Move, Pokemon, PokemonFactory}
import view.battle.DialogueBox

import scala.io.Source

class FightLayout(var layoutData: Pokemon, skin: Skin, boundary: Rectangle, actionPerformed: Int => Unit) extends BaseLayout[Pokemon, Int](layoutData, boundary, actionPerformed):
  val box: DialogueBox = DialogueBox(Seq("Scegli una mossa"), skin)
  add(box).colspan(2)
  row()
  generateTable
  setSize(boundary.width, boundary.height)
  setPosition(boundary.x, boundary.y)
  setVisible(false)

  def generateButtons: Seq[(Int, ImageTextButton)] =
      for
        i <- layoutData.moves.indices
        b = ImageTextButton(layoutData.moves(i).name, skin)
        checkedButton = {b.addListener(listener(i)); checkPP(b, layoutData.moves(i))}
      yield (i, checkedButton)

  def checkPP(button: ImageTextButton, move: Move): ImageTextButton =
    if move.powerPoint <= 0 then
      button.setDisabled(true)
    else
      button.setDisabled(false)
    button

  def generateTable: Unit =
    for (elem <- generateButtons)
      add(elem._2).fill().pad(10)
      if (elem._1 + 1) % 2 == 0 then
        row()

  override def update(newLayoutInfo: Pokemon): Unit =
    layoutData = newLayoutInfo
    for
      (index,button) <- generateButtons
      cell = getCells.items(index+1)
    do
      cell.getActor match
        case _: ImageTextButton =>  cell.setActor(button)
        case _ =>

