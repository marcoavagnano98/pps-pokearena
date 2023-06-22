package model.entities.pokemon

import model.entities.pokemon.{Move, Pokemon, PokemonStatus}

import scala.util.Random

trait Move:
  def damage: Int

  def powerPoint: Int

  def name: String

  def elementType: ElementType

  def status: PokemonStatus

  def applyStatus(p: Pokemon): Pokemon


