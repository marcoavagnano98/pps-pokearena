package model.entities

enum DoorState:
  case Open, Close

trait Door extends VisibleEntity:
  /**
   * @return the state of the [[Door]]
   *
   */
  def state: DoorState

