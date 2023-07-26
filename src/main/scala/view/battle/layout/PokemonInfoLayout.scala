package view.battle.layout

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Skin, Table}
import model.entities.pokemon.AllPokemonStatus.HealthyStatus
import view.battle.DialogueBox
import model.entities.pokemon.Pokemon
import view.Sprites


class PokemonInfoLayout(var layoutData: Pokemon, skin: Skin, boundary: Rectangle) extends BaseLayout(boundary):
  override type T = Pokemon
  add(pokemonImage).padRight(30).height(150).width(150)
  add(infoBox)

  private def infoBox: DialogueBox =
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

  private def pokemonImage: Image = Image(Texture(Sprites.getBattleSprite(layoutData.id)))

  override def updateLayout(newLayoutInfo: Pokemon): Unit =
    layoutData = newLayoutInfo
    updateActorByIndex(pokemonImage, 0)
    updateActorByIndex(infoBox, 1)


