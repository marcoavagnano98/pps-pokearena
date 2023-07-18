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

class BattleMenuLayout(val layoutInfo: String, skin: Skin, rect: Rectangle, actionPerformed: BattleMenuOption => Unit) extends Table with Layout[String]:

  import BattleMenuOption.*

  val box: DialogueBox = DialogueBox(Seq("Cosa deve fare " + layoutInfo + "?"), skin)
  add(box).colspan(2)
  box.setDebug(true)
  row()
  val bagButton: ImageTextButton = ImageTextButton("ZAINO", skin)
  bagButton.addListener(buttonListener(BagOption))
  add(bagButton).fill().pad(10).minHeight(50)
  val fightButton: ImageTextButton = ImageTextButton("LOTTA", skin)
  fightButton.addListener(buttonListener(FightOption))
  add(fightButton).fill().pad(10).minHeight(50)
  setSize(rect.width, rect.height)
  setPosition(rect.x, rect.y)

  private def buttonListener(action: BattleMenuOption): ClickListener =
    new ClickListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
        super.touchDown(event, x, y, pointer, button)
        actionPerformed(action)
        true
    }

  override def update(newLayoutInfo: String): Unit = {}
  





