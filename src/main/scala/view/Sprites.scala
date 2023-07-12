package view

import model.entities.{Trainer, VisibleEntity}
import model.entities.pokemon.Pokemon

object Sprites:
  def getPokemonSprite(pokemon: Pokemon) : String =
    "sprites/pokedex/" + pokemon.id + ".png"
    
  def getPlayerSprite(trainer: VisibleEntity): String = "assets/trainers/"+ trainer.id+ ".png"
  def getSpritePokemonId(id: String): String = "sprites/pokedex/" + id + ".png"