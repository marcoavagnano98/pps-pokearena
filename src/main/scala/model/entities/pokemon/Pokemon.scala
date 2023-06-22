package model.entities.pokemon

import model.entities.pokemon.PokemonStatus
import model.entities.Entity

trait Pokemon extends Entity:
  def hp: Int

  def attack: Int

  def defense: Int

  def speed: Int

  def moves: Seq[Move]

  def elementType: ElementType

  def status: PokemonStatus

  def withHp(hp: Int): Pokemon

  def withStatus(status: PokemonStatus): Pokemon

  def withMoves(moves: Seq[Move]) : Pokemon


object Pokemon:
  def apply(id: String, hp: Int, attack: Int, defense: Int, speed: Int, moves: Seq[Move], elementType: ElementType): Pokemon =
    PokemonImpl(id = id, hp = hp, attack = attack, defense = defense, speed = speed, moves = moves, elementType = elementType)

  private case class PokemonImpl(override val height: Int = 5,
                                 override val width: Int = 5,
                                 override val id: String,
                                 override val hp: Int,
                                 override val attack: Int,
                                 override val defense: Int,
                                 override val speed: Int,
                                 override val moves: Seq[Move],
                                 override val status: PokemonStatus = HealthyStatus(),
                                 override val elementType: ElementType
                                ) extends Pokemon:

    override def withHp(newHp: Int): Pokemon = copy(hp = newHp)

    override def withStatus(newStatus: PokemonStatus): Pokemon = copy(status = newStatus)

    override def withMoves(newMoves: Seq[Move]) : Pokemon = copy(moves = newMoves)
