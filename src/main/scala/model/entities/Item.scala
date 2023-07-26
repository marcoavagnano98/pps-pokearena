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
  def use(pokemon: Pokemon): Pokemon

private class Potion(override val id: String = "Item_01", override val position: Position, override val name: String = "Potion") extends Item with ChangeHpEffect :
  override def hpToChange: Int = 50

  override def use(p: Pokemon): Pokemon =
    applyChangeStat(p)

private class SuperPotion(override val position: Position) extends Potion("Item_02", position, "Super Potion") :
  override def hpToChange: Int = 100

enum ItemType:
  case Potion, SuperPotion

object ItemFactory:
  def apply(itemType: ItemType, pos: Position = Position(0, 0)): Item = itemType match
    case ItemType.Potion => Potion(position = pos)
    case ItemType.SuperPotion => SuperPotion(position = pos)

  def getRandomItem(pos: Position): Item =
    import scala.util.Random
    Random.nextInt(ItemType.values.length) match
      case 1 => apply(ItemType.SuperPotion, pos)
      case _ => apply(ItemType.Potion, pos)

