package model.entities

import model.entities.World.Position
import model.entities.pokemon.AllPokemonStatus.HealthyStatus
import model.entities.pokemon.Pokemon
import model.entities.pokemon.StatusEffects.{ChangeHpEffect, StatusEffect}

/**
 * Represent the Entity that can be collected during the game
 */
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

/**
 * Represent the [[Potion]] that can be collected during the game
 * @param id the identifier of the Potion, for the rendering
 * @param position the [[Position]] where the [[Potion]] should be rendered
 * @param name the discriminant through which the [[Item]] can be identified
 */
private class Potion(override val id: String = "Item_01", override val position: Position, override val name: String = "Potion") extends Item with ChangeHpEffect :
  override def hpToChange: Int = 50
  override def use(p: Pokemon): Pokemon = applyChangeStat(p)

/**
 * Represent the [[SuperPotion]] that can be collected during the game
 * @param position the [[Position]] where the SuperPotion should be rendered
 */
private class SuperPotion(override val position: Position) extends Potion("Item_02", position, "Super Potion") :
  override def hpToChange: Int = 100

/**
 * Represent the Antidote that can be collected during the game
 * @param id the identifier of the [[Antidote]], for the rendering
 * @param position the [[Position]] where the [[Antidote]] should be rendered
 * @param name the discriminant through which the [[Item]] can be identified
 */
private class Antidote(override val id: String = "Item_03", override val position: Position,override val name: String = "Antidote") extends Item with StatusEffect:
  override type Result = Pokemon
  override def applyEffect(pokemon: Pokemon): Result = pokemon withStatus HealthyStatus()
  override def use(pokemon: Pokemon): Pokemon = applyEffect(pokemon)


enum ItemType:
  case Potion, SuperPotion, Antidote

/**
 * Factory used to create different type of [[Item]]
 */
object ItemFactory:
  def apply(itemType: ItemType, pos: Position = Position(0, 0)): Item = itemType match
    case ItemType.Potion => Potion(position = pos)
    case ItemType.SuperPotion => SuperPotion(position = pos)
    case ItemType.Antidote => Antidote(position = pos)


  /**
   * @param pos the [[Position]] where the [[Item]] should be rendered
   * @return A random [[Item]] among those available.
   */
  def getRandomItem(pos: Position): Item =
    import scala.util.Random
    Random.nextInt(ItemType.values.length) match
      case 1 => apply(ItemType.SuperPotion, pos)
      case 2 => apply(ItemType.Antidote, pos)
      case _ => apply(ItemType.Potion, pos)

