package model.entities

import model.entities.World.Position

trait Entity:
  /**
   *
   * @return width of [[Entity]]
   */
  def width: Float = EntityDefaultValues.height(this)

  /**
   *
   * @return height of [[Entity]]
   */
  def height: Float = EntityDefaultValues.width(this)

  /**
   *
   * @return id of [[Entity]]
   */
  def id: String

trait VisibleEntity extends Entity :
  /**
   *
   * @return position of [[VisibleEntity]]
   */
  def position: Position

trait MovingAbility:
  /**
   * @param position the new position of the entity
   * @return the same [[Entity]] with position updated
   */
  def updatePosition(position: Position): VisibleEntity

/**
 * This object contains the default values for each type of [[Entity]].
 */
object EntityDefaultValues:

  import model.entities.pokemon.Pokemon
  import model.entities.Trainer

  /**
   * Given an [[Entity]] it returns its width.
   */
  val width: Entity => Float =
    case _: Pokemon => 5
    case _: Trainer => 100
    case _ => 0

  /**
   * Given an [[Entity]] it returns its height.
   */
  val height: Entity => Float =
    case _: Pokemon => 5
    case _: Trainer => 100
    case _ => 0