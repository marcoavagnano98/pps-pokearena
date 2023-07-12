package model.entities.pokemon

import model.entities.Entity

import model.entities.pokemon.PokemonStatus
import model.entities.pokemon.AllPokemonStatus.HealthyStatus

trait Pokemon extends Entity :
  /**
   * @return the name of the [[Pokemon]]
   */
  def name: String

  /**
   * @return the hp of the [[Pokemon]]
   */
  def hp: Int

  /**
   * @return the attack of the [[Pokemon]]
   */
  def attack: Int

  /**
   * @return the defense of the [[Pokemon]]
   */
  def defense: Int

  /**
   * @return the speed of the [[Pokemon]]
   */
  def speed: Int

  /**
   * @return the sequence of [[Move]] of the [[Pokemon]]
   */
  def moves: Seq[Move]

  /**
   * @return the elementType of the [[Pokemon]]
   */
  def elementType: ElementType

  /**
   * @return the status of the [[Pokemon]]
   */
  def status: PokemonStatus

  /**
   * @param hp The new hp of the [[Pokemon]].
   * @return The same [[Pokemon]] with the hp updated.
   */
  def withHp(hp: Int): Pokemon

  /**
   * @param status The new [[PokemonStatus]] of the [[Pokemon]].
   * @return The same [[Pokemon]] with the [[PokemonStatus]] updated.
   */
  def withStatus(status: PokemonStatus): Pokemon

  /**
   * @param moves The new sequence of [[Move]] of the [[Pokemon]].
   * @return The same [[Pokemon]] with the sequence of [[Move]] updated.
   */
  def withMoves(moves: Seq[Move]): Pokemon

  /**
   * @param speed The new speed of the [[Pokemon]].
   * @return The same [[Pokemon]] with the speed updated.
   */
  def withSpeed(speed: Int): Pokemon

  /**
   * @param atk The new atk of the [[Pokemon]].
   * @return The same [[Pokemon]] with the atk updated.
   */
  def withAtk(atk: Int): Pokemon

object Pokemon:
  def apply(id: String, name: String, hp: Int, attack: Int, defense: Int, speed: Int, moves: Seq[Move], elementType: ElementType): Pokemon =
    PokemonImpl(id = id, name = name, hp = hp, attack = attack, defense = defense, speed = speed, moves = moves, elementType = elementType, maxHp = hp)

  private case class PokemonImpl(override val name: String,
                                 override val id: String,
                                 override val hp: Int,
                                 override val attack: Int,
                                 override val defense: Int,
                                 override val speed: Int,
                                 override val moves: Seq[Move],
                                 override val status: PokemonStatus = HealthyStatus(),
                                 override val elementType: ElementType,
                                 maxHp: Int
                                ) extends Pokemon :

    override def withHp(newHp: Int): Pokemon = newHp match
      case hp if hp < 0 => copy(hp = 0)
      case hp if hp > maxHp => copy(hp = maxHp)
      case _ => copy(hp = newHp)

    override def withStatus(newStatus: PokemonStatus): Pokemon = copy(status = newStatus)

    override def withMoves(newMoves: Seq[Move]): Pokemon = copy(moves = newMoves)

    override def withSpeed(newSpeed: Int): Pokemon = copy(speed = newSpeed)

    override def withAtk(newAtk: Int): Pokemon = copy(attack = newAtk)