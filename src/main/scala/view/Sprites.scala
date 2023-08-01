package view


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import model.entities.{Door, Item, Trainer, VisibleEntity}
import model.entities.pokemon.Pokemon

import scala.util.Random

protected[view] object Sprites:
  val background = "assets/pokemon_grass.png"
  val separatorLine = "assets/blackline.png"
  val getPokemonSprite : Pokemon => String = "sprites/pokedex/" + _.id + ".png"
  val getBattleSprite: String => String = "sprites/pokedex/battle/" + _ + ".png"
  val getMapPath: String => String = "assets/rooms/" + _ + ".png"

  val getEntitySprite : VisibleEntity => String =
    case trainer: Trainer => "assets/trainers/"+ trainer.id+ ".png"
    case item: Item => "assets/items/"+ item.id+ ".png"
    case door: Door => "assets/doors/"+ door.id +".png"

