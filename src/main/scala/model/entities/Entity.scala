package model.entities

import model.entities.World.Position

trait Entity:
  /**
   *
   * @return width of [[Entity]]
   */
  def width: Int

  /**
   *
   * @return height of [[Entity]]
   */
  def height: Int

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
   * @return the same Entity with position updated
   */
  def updatePosition(position: Position): VisibleEntity