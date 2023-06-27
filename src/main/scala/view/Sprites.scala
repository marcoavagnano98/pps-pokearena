package view

import model.entities.pokemon.Pokemon

object Sprites:
  def getPokemonSprite(pokemon: Pokemon) : String =
    "/sprites/pokedex/" + pokemon.id + ".png"
