package view

import model.entities.{Trainer, VisibleEntity}
import model.entities.pokemon.Pokemon

import scala.util.Random

object Sprites:
  def getPokemonSprite(pokemon: Pokemon) : String = "sprites/pokedex/" + pokemon.id + ".png"
  def getPlayerSprite(trainer: VisibleEntity): String = "assets/trainers/"+ trainer.id+ ".png"
  def getMapSprite(id: String): String = "assets/rooms/" + id + Random.between(0, 13) + ".png"
  def getSpritePokemonId(id: String): String = "sprites/pokedex/" + id + ".png"
  def getBattleSprite(id: String): String = "sprites/pokedex/battle/" + id + ".png"