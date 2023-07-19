package model.entities

import model.entities.World.Position
import model.entities.pokemon.Pokemon
import model.entities.pokemon.StatusEffects.ChangeHpEffect

trait Item extends VisibleEntity :
  /**
   * @return the name of [[Item]]
   */
  def name: String

  /**
   * @param pokemon The [[Pokemon]] to which the [[Item]] was used on.
   * @return The new [[Pokemon]] after the [[Item]] was applied.
   */
  def use(p: Pokemon): Pokemon

private case class Potion(override val id: String = "Item_01", override val position: Position, override val name: String = "Potion") extends Item with ChangeHpEffect :
  override def hpToChange: Int = 20

  override def use(p: Pokemon): Pokemon =
    applyChangeStat(p)

private case class SuperPotion(override val id: String = "Item_02", override val position: Position, override val name: String = "Potion") extends Item with ChangeHpEffect :
  override def hpToChange: Int = 50

  override def use(p: Pokemon): Pokemon =
    applyChangeStat(p)
    

enum ItemId:
  case Potion, SuperPotion

object ItemFactory:
  def apply(itemId: ItemId, pos: Position = Position(0,0)) : Item = itemId match
    case ItemId.Potion => Potion(position=pos)
    case ItemId.SuperPotion => SuperPotion(position=pos)

  def getRandomItem(pos: Position) : Item =
    import scala.util.Random
    Random.nextInt(ItemId.values.length) match
      case 1 => apply(ItemId.SuperPotion,pos)
      case _ => apply(ItemId.Potion,pos)

