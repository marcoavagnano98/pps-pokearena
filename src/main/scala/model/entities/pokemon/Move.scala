package model.entities.pokemon

import model.entities.pokemon.{Move, Pokemon, PokemonStatus, PokemonStatusWithEffect}
import scala.util.Random
import util.Utilities.*

trait Move:
  /**
   * @return the damage of [[Move]]
   */
  def damage: Int

  /**
   * @return the powerPoint of [[Move]]
   */
  def powerPoint: Int

  /**
   * @return the name of [[Move]]
   */
  def name: String

  /**
   * @return the [[ElementType]] of [[Move]]
   */
  def elementType: ElementType

  /**
   * @return the [[PokemonStatus]] of [[Move]]
   */
  def status: Option[PokemonStatus]

  /**
   * @param pokemon The [[Pokemon]] to which the [[PokemonStatus]] is applied.
   * @return The new [[Pokemon]] with the [[PokemonStatus]] updated.
   */
  def applyStatus(pokemon: Pokemon): Pokemon

  /**
   * @return The update [[Move]] with the [[powerPoint]] reduce by one.
   */
  def withReducePowerPoint(): Move
  
object Move:
  def apply(damage: Int, powerPoint: Int, name: String, elementType: ElementType, status: Option[PokemonStatus]): Move =
    MoveImpl(damage, powerPoint, name, elementType, status)

  private case class MoveImpl(override val damage: Int,
                              override val powerPoint: Int,
                              override val name: String,
                              override val elementType: ElementType,
                              override val status: Option[PokemonStatus],
                             ) extends Move :
    
    override def withReducePowerPoint() : Move = copy(powerPoint=powerPoint-1)
    
    override def applyStatus(pokemon: Pokemon): Pokemon = status match
      case Some(status:PokemonStatusWithEffect) if pokemon.status != status => status applyStatus pokemon
      case _ => pokemon





