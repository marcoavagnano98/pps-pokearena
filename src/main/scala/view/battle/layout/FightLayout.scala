package view.battle.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Cell, ImageTextButton, Skin, Table}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import model.entities.pokemon.{Move, Pokemon, PokemonFactory}
import view.battle.DialogueBox

import scala.io.Source

class FightLayout(var pokemon: Pokemon, skin: Skin, rect: Rectangle, actionPerformed: Int => Unit) extends Table:
  val box: DialogueBox = DialogueBox("Scegli una mossa", skin)
  add(box).colspan(2)
  row()
  generateTable
  setSize(rect.width, rect.height)
  setPosition(rect.x, rect.y)

  private def moveButtonListener(index: Int): ClickListener= new ClickListener(){
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
      super.touchDown(event, x, y, pointer, button)
      actionPerformed(index)
      true
  }
  def generateButtons: Seq[(Int, ImageTextButton)] =
      for
        i <- pokemon.moves.indices
        b = ImageTextButton(pokemon.moves(i).name, skin)
        checkedButton = {b.addListener(moveButtonListener(i)); checkPP(b, pokemon.moves(i))}
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

  def update(p: Pokemon): Unit =
    pokemon = p
    for
      (index,button) <- generateButtons
      cell = getCells.items(index)
    do
      cell.getActor match
        case _: ImageTextButton => cell.setActor(button)
        case _ =>