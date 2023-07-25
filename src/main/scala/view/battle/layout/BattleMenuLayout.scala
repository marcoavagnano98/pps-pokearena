package view.battle.layout

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Touchable}
import com.badlogic.gdx.scenes.scene2d.ui.{ImageTextButton, Skin, Table}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import model.entities.pokemon.Pokemon
import view.battle.DialogueBox

enum BattleMenuOption:
  case BagOption, FightOption

class BattleMenuLayout(var layoutData: Seq[String], skin: Skin, boundary: Rectangle, callback: BattleMenuOption => Unit) extends BaseLayout[BattleMenuOption](boundary, callback):
  import BattleMenuOption.*
  override type T = Seq[String]
  val startInfoBox: DialogueBox = generateInfoBox(layoutData)
  val bagButton: ImageTextButton = ImageTextButton("ZAINO", skin)
  bagButton.addListener(listener(BagOption))
  val fightButton: ImageTextButton = ImageTextButton("LOTTA", skin)
  fightButton.addListener(listener(FightOption))
  add(startInfoBox).colspan(2)
  row()
  add(bagButton).fill().pad(10).minHeight(50)
  add(fightButton).fill().pad(10).minHeight(50)
  bagEnabled(Touchable.disabled)

  private def generateInfoBox(text: Seq[String]) : DialogueBox = DialogueBox(text, skin)

  def hideButtonMenu: Unit =
    bagButton.setVisible(false)
    fightButton.setVisible(false)

  def showButtonMenu: Unit =
    bagButton.setVisible(true)
    fightButton.setVisible(true)

  def bagEnabled(touchable: Touchable): Unit =
    bagButton.setTouchable(touchable)
    bagButton.setDisabled(true)

  override def update(newLayoutInfo: Seq[String]): Unit =
    layoutData = newLayoutInfo
    getCells.items(0).getActor match
      case _: DialogueBox =>  getCells.items(0).setActor(generateInfoBox(newLayoutInfo))
      case _ =>