package model.entities

import model.entities.World.Position

/**
 * The State of the Door. Can be Open or Close
 */
enum DoorState:
  case Open, Close

/**
 * Represents the Entity through which it's possible to access the next level
 */
trait Door extends VisibleEntity:
  /**
   *
   * @return the state of the [[Door]]
   */
  def state: DoorState

  /**
   * Updates the information of the [[Door]]
   * @param idDoor the String representing the name of the image to be rendered
   * @param doorState the state with which you want to set the doors: Open/Close
   * @return
   */
  def updateDoor(idDoor: String, doorState: DoorState): Door

object Door:
  def apply(state: DoorState, position: Position): Door =
    DoorImpl("door_close", position, state)

  private case class DoorImpl(override val id: String, override val position: Position, override val state: DoorState) extends Door:
    override def updateDoor(idDoor: String, doorState: DoorState): Door = copy(id=idDoor, state=doorState)