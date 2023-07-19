package view.battle.layout

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, Table}
import model.entities.pokemon.AllPokemonStatus.HealthyStatus
import view.battle.DialogueBox
import model.entities.pokemon.Pokemon
class PokemonInfoLayout(var layoutInfo: Pokemon, skin: Skin, rect:Rectangle) extends Table with Layout[Pokemon]:
  add(infoBox)
  setSize(rect.width, rect.height)
  setPosition(rect.x, rect.y)

  def infoBox: DialogueBox =
    val infoBox = DialogueBox(Seq(
      layoutInfo.name + " [" + layoutInfo.elementType.elemType +"]",
      layoutInfo.hp + "/" + layoutInfo.hp,
      {
        layoutInfo.status match
          case _: HealthyStatus => ""
          case _ => layoutInfo.status.name
      }),
      skin)
    infoBox.pad(5)
    infoBox
    
  override def update(newLayoutInfo: Pokemon): Unit =
    layoutInfo = newLayoutInfo
    getCells.items(0).setActor(infoBox)


