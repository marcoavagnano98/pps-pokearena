package model.entities.pokemon

import model.entities.pokemon.{Move, Pokemon, PokemonStatus, PokemonStatusWithEffect}
import scala.util.Random
import util.Utilities.*

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
                             ) extends Move :

    override def applyStatus(p: Pokemon): Pokemon =
      if p.status != status then
        status match
          case s: PokemonStatusWithEffect => p withStatus s
          case _ => p
      else
        p
//      case s:PokemonStatusWithEffect if randomDice(s.probabilityForApplyStatus)=>
//        val newP = p withStatus s
//        s match
//          case s:PersistentEffect => s applyPersistentEffect newP
//          case _ => newP
//      case _ => p











