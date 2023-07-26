package view.battle.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Touchable}
import com.badlogic.gdx.scenes.scene2d.ui.{ImageTextButton, Skin, Table}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import model.entities.pokemon.Pokemon
import view.battle.DialogueBox
import view.GdxUtil.onTouchDown

enum BattleMenuOption:
  case BagOption, FightOption

case class BattleMenuLayout(var layoutData: Seq[String], skin: Skin, boundary: Rectangle, callback: BattleMenuOption => Unit) extends BaseLayout(boundary) :

  import BattleMenuOption.*
  import LayoutVisibility.*

  override type T = Seq[String]

  add(infoBox).colspan(2)
  row()
  for button <- menuButtons
    do add(button).fill().pad(10).minHeight(50)
  setButtonsVisibility(Visible)
  bagButtonTouchable(Touchable.disabled)

  private def infoBox: DialogueBox = DialogueBox(layoutData, skin)

  lazy val menuButtons: Seq[ImageTextButton] =
    val fightButton: ImageTextButton = ImageTextButton("LOTTA", skin)
    val bagButton: ImageTextButton = ImageTextButton("ZAINO", skin)
    fightButton.onTouchDown(callback, FightOption)
    bagButton.onTouchDown(callback, BagOption)
    Seq(fightButton, bagButton)

  def setButtonsVisibility(visibility: LayoutVisibility): Unit =
    val seq: Seq[ImageTextButton] = menuButtons
    for i <- seq.indices
      do
      seq(i).setVisible(visibility.value)
      updateActorByIndex(seq(i), i + 1)

  def bagButtonTouchable(touchable: Touchable): Unit =
    val bagButton: ImageTextButton = menuButtons(1)
    bagButton.setTouchable(touchable)
    updateActorByIndex(bagButton, 2)


  override def updateLayout(newLayoutInfo: Seq[String]): Unit =
    layoutData = newLayoutInfo
    updateActorByIndex(infoBox, 0)