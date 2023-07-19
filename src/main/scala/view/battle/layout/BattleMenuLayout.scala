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

class BattleMenuLayout(var layoutInfo: Seq[String], skin: Skin, rect: Rectangle, actionPerformed: BattleMenuOption => Unit) extends Table with Layout[Seq[String]]:

  import BattleMenuOption.*
  val startInfoBox: DialogueBox = generateInfoBox(layoutInfo)
  val bagButton: ImageTextButton = ImageTextButton("ZAINO", skin)
  bagButton.addListener(buttonListener(BagOption))
  val fightButton: ImageTextButton = ImageTextButton("LOTTA", skin)
  fightButton.addListener(buttonListener(FightOption))
  add(startInfoBox).colspan(2)
  row()
  add(bagButton).fill().pad(10).minHeight(50)
  add(fightButton).fill().pad(10).minHeight(50)
  setSize(rect.width, rect.height)
  setPosition(rect.x, rect.y)

  private def generateInfoBox(text: Seq[String]) : DialogueBox = DialogueBox(text, skin)

  def hideButtonMenu: Unit =
    bagButton.setVisible(false)
    fightButton.setVisible(false)

  def showButtonMenu: Unit =
    bagButton.setVisible(true)
    fightButton.setVisible(true)

  private def buttonListener(action: BattleMenuOption): ClickListener =
    new ClickListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        super.touchDown(event, x, y, pointer, button)
        actionPerformed(action)
        true
    }
    
  override def update(newLayoutInfo: Seq[String]): Unit =
    layoutInfo = newLayoutInfo
    getCells.items(0).getActor match
      case _: DialogueBox =>  getCells.items(0).setActor(generateInfoBox(newLayoutInfo))
      case _ =>  
    
  





