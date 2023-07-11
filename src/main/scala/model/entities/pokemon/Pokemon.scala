package model.entities.pokemon

import model.entities.Entity

import model.entities.pokemon.PokemonStatus
import model.entities.pokemon.AllPokemonStatus.HealthyStatus

trait Pokemon extends Entity :
  def name: String

  def hp: Int

  def attack: Int

  def defense: Int

  def speed: Int

  def moves: Seq[Move]

  def elementType: ElementType

  def status: PokemonStatus

  def withHp(hp: Int): Pokemon

  def withStatus(status: PokemonStatus): Pokemon

  def withMoves(moves: Seq[Move]): Pokemon

  def withSpeed(speed: Int): Pokemon

  def withAtk(atk: Int): Pokemon

object Pokemon:
  def apply(id: String, name: String, hp: Int, attack: Int, defense: Int, speed: Int, moves: Seq[Move], elementType: ElementType): Pokemon =
    PokemonImpl(id = id, name = name, hp = hp, attack = attack, defense = defense, speed = speed, moves = moves, elementType = elementType)
  
  private case class PokemonImpl(override val name: String,
                                 override val id: String,
                                 override val hp: Int,
                                 override val attack: Int,
                                 override val defense: Int,
                                 override val speed: Int,
                                 override val moves: Seq[Move],
                                 override val status: PokemonStatus = HealthyStatus(),
                                 override val elementType: ElementType
                                ) extends Pokemon :
    private val maxHp = hp

    override def withHp(newHp: Int): Pokemon = newHp match 
      case hp if hp < 0 => copy(hp = 0)
      case hp if hp > maxHp => copy(hp=maxHp)
      case _ => copy(hp=newHp)

    override def withStatus(newStatus: PokemonStatus): Pokemon = copy(status = newStatus)

    override def withMoves(newMoves: Seq[Move]): Pokemon = copy(moves = newMoves)

    override def withSpeed(newSpeed: Int): Pokemon = copy(speed = newSpeed)

    override def withAtk(newAtk: Int): Pokemon = copy(attack = newAtk)