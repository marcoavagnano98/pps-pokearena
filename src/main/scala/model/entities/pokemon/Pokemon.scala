package model.entities

import model.entities.ElementType
import model.entities.PokemonStatus
trait Pokemon extends Entity:
  def hp: Int

  def attack: Int

  def defense: Int

  def speed: Int

  def moves: List[Move]

  def elementType: ElementType

  def status: PokemonStatus

  def withHp(hp: Int): Pokemon

  def withStatus(status: PokemonStatus): Pokemon


object Pokemon:

  def apply(id: String, hp: Int, attack: Int, defense: Int, speed: Int, moves: List[Move], status: PokemonStatus): Pokemon = ???

  private case class PokemonImpl(override val height: Int = 5,
                                 override val width: Int,
                                 override val id: String,
                                 override val hp: Int,
                                 override val attack: Int,
                                 override val defense: Int,
                                 override val speed: Int,
                                 override val moves: List[Move],
                                 override val status: PokemonStatus,
                                 override val elementType: ElementType
                                ) extends Pokemon:

    override def withHp(newHp: Int): Pokemon = copy(hp = newHp)
    override def withStatus(newStatus: PokemonStatus): Pokemon = copy(status = newStatus)

/*
trait ElementType
case class Fire() extends ElementType
case class Pokemon[T <: ElementType](override val height:Int,
                                     override val width:Int,
                                     override val id:String,
                                     elementType: T,
                                     override val hp:Int,
                                     override val attack:Int,
                                     override val defense:Int,
                                     override val speed:Int,
                                     override val moves:List[Move]) extends BasePokemon:
  override type ElementType = T

object main:
  @main def test() =
    val p = Pokemon[Fire](10,20,"ciao",new Fire,13,14,13,14,List())
    print(p.elementType)
*/


