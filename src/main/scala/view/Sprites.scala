package view

import model.entities.Trainer
import model.entities.pokemon.Pokemon

object Sprites:
  def getPokemonSprite(pokemon: Pokemon) : String =
    "sprites/pokedex/" + pokemon.id + ".png"
    
  def getPlayerSprite(trainer: Trainer): String = "sprites/trainers/"+ trainer.id+ ".png"
  def getSpritePokemonId(id: String): String = "sprites/pokedex/" + id + ".png"