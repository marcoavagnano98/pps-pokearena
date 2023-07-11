package model.entities.pokemon

import model.entities.pokemon.{Move, Pokemon, PokemonStatus, PokemonStatusWithEffect}
import scala.util.Random
import util.Utilities.*

trait Move:
  def damage: Int

  def powerPoint: Int

  def name: String

  def elementType: ElementType

  def status: Option[PokemonStatus]

  def applyStatus(p: Pokemon): Pokemon


object Move:
  def apply(damage: Int, powerPoint: Int, name: String, elementType: ElementType, status: Option[PokemonStatus]): Move =
    MoveImpl(damage, powerPoint, name, elementType, status)

  private case class MoveImpl(override val damage: Int,
                              override val powerPoint: Int,
                              override val name: String,
                              override val elementType: ElementType,
                              override val status: Option[PokemonStatus],
                             ) extends Move :

    override def applyStatus(p: Pokemon): Pokemon =
      if p.status != status && status.nonEmpty then
        status.get match
          case s: PokemonStatusWithEffect => p withStatus s
          case _ => p
      else
        p







