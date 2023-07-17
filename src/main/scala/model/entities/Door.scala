package model.entities

import model.entities.World.Position

enum DoorState:
  case Open, Close

trait Door extends VisibleEntity:
  /**
   * @return the state of the [[Door]]
   *
   */
  def state: DoorState

object Door:
  def apply(state: DoorState, position: Position): Door =
    DoorImpl("door_close", position, state)

  private case class DoorImpl(override val id: String, override val position: Position, state: DoorState) extends Door
