package view

import model.entities.{Door, Potion, Trainer, VisibleEntity}
import model.entities.pokemon.Pokemon

import scala.util.Random

object Sprites:
  private val numberOfMaps = 13

  def getPokemonSprite(pokemon: Pokemon) : String = "sprites/pokedex/" + pokemon.id + ".png"
  def getEntitySprite(entity: VisibleEntity): String = entity match
    case trainer: Trainer => "assets/trainers/"+ trainer.id+ ".png"
    case potion: Potion => "assets/items/"+ potion.id+ ".png"
    case door: Door => "assets/doors/"+ door.id +".png"

  def getMapSprite(id: String): String = "assets/rooms/" + id + Random.between(0, numberOfMaps) + ".png"
  def getSpritePokemonId(id: String): String = "sprites/pokedex/" + id + ".png"
  def getBattleSprite(id: String): String = "sprites/pokedex/battle/" + id + ".png"