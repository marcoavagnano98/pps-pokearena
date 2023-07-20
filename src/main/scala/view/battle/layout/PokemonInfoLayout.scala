package view.battle.layout

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, Table}
import model.entities.pokemon.AllPokemonStatus.HealthyStatus
import view.battle.DialogueBox
import model.entities.pokemon.Pokemon

class PokemonInfoLayout(var layoutData: Pokemon, skin: Skin, boundary: Rectangle, actionPerformed: Unit => Unit = (_: Unit) => {}) extends BaseLayout[Pokemon, Unit](layoutData, boundary, actionPerformed) :
  add(infoBox)
  setSize(boundary.width, boundary.height)
  setPosition(boundary.x, boundary.y)

  def infoBox: DialogueBox =
    val infoBox = DialogueBox(Seq(
      layoutData.name + " [" + layoutData.elementType.elemType + "]",
      layoutData.hp + "/" + layoutData.maxHp,
      {
        layoutData.status match
          case _: HealthyStatus => ""
          case _ => layoutData.status.name
      }),
      skin)
    infoBox.pad(5)
    infoBox

  override def update(newLayoutInfo: Pokemon): Unit =
    layoutData = newLayoutInfo
    getCells.items(0).setActor(infoBox)


