package view.battle.layout

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, Table}
import view.battle.DialogueBox
import model.entities.pokemon.Pokemon
class PokemonInfoLayout(var layoutInfo: Pokemon, skin: Skin, rect:Rectangle) extends Table with Layout[Pokemon]:
  add(infoBox)
  setSize(rect.width, rect.height)
  setPosition(rect.x, rect.y)
  
  def infoBox: DialogueBox = 
    val infoBox = DialogueBox(Seq(layoutInfo.name, layoutInfo.hp + "/" + layoutInfo.hp), skin)
    infoBox.pad(5)
    infoBox
    
  override def update(newLayoutInfo: Pokemon): Unit =
    layoutInfo = newLayoutInfo
    getCells.items(0).setActor(infoBox)


