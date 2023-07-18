package model.entities

import model.entities.World.Position
import model.entities.pokemon.Pokemon
import model.entities.pokemon.StatusEffects.ChangeHpEffect

trait Item extends VisibleEntity :
  def name: String

  def use(p: Pokemon): Pokemon

private class Potion(override val id: String = "Item_01", override val position: Position, override val name: String = "Potion") extends Item with ChangeHpEffect :
  override def hpToChange: Int = 20

  override def use(p: Pokemon): Pokemon =
    applyChangeStat(p)

private class SuperPotion(override val id: String = "Item_02", override val position: Position, override val name: String = "Potion") extends Item with ChangeHpEffect :
  override def hpToChange: Int = 50

  override def use(p: Pokemon): Pokemon =
    applyChangeStat(p)

enum ItemId:
  case Potion, SuperPotion

object ItemFactory:
  def apply(itemId: ItemId, position: Position) : Item = itemId match
    case ItemId.Potion => Potion(position=position)
    case ItemId.SuperPotion => SuperPotion(position=position)

  def getRandomItem(position: Position) : Item =
    import scala.util.Random
    Random.nextInt(ItemId.values.length) match
      case 1 => apply(ItemId.SuperPotion,position)
      case _ => apply(ItemId.Potion,position)

