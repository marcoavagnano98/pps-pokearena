package view


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import model.entities.{Door, Item, Trainer, VisibleEntity}
import model.entities.pokemon.Pokemon

import scala.util.Random

object Sprites:
  private val numberOfMaps = 13

  def getPokemonSprite(pokemon: Pokemon) : String = "sprites/pokedex/" + pokemon.id + ".png"
  def getEntitySprite(entity: VisibleEntity): String = entity match
    case trainer: Trainer => "assets/trainers/"+ trainer.id+ ".png"
    case item: Item => "assets/items/"+ item.id+ ".png"
    case door: Door => "assets/doors/"+ door.id +".png"
  def getGenericItem = "assets/items/Item_00.png"
  def getMapPath(id: String): String = "assets/rooms/" + id + Random.between(0, numberOfMaps) + ".png"
  def getSpritePokemonId(id: String): String = "sprites/pokedex/" + id + ".png"
  def getBattleSprite(id: String): String = "sprites/pokedex/battle/" + id + ".png"

  def texture (path:String) = new Texture(Gdx.files.classpath(path))
  object MemoHelper:
    def memoize[I, O](f: I => O): I => O = new collection.mutable.HashMap[I, O]() :
      override def apply(key: I): O = getOrElseUpdate(key, f(key))