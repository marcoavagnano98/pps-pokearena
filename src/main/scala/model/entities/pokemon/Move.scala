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


object Move:
  def apply(damage: Int, powerPoint: Int, name: String, elementType: ElementType, status: PokemonStatus): Move =
    MoveImpl(damage, powerPoint, name, elementType, status)

  private case class MoveImpl(override val damage: Int,
                              override val powerPoint: Int,
                              override val name: String,
                              override val elementType: ElementType,
                              override val status: PokemonStatus,
                             ) extends Move:
    private val probabiltyForApplyStatus = 30

    override def applyStatus(p: Pokemon): Pokemon =
      val rand = Random()
      if (rand.between(1, 100) <= probabiltyForApplyStatus) then
        p withStatus status
      p





