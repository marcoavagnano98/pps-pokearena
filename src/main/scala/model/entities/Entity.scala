package model.entities

import model.entities.World.Position
import World.Position

trait Entity :
  def width: Int
  def height: Int
  def id: String


trait VisibleEntity extends Entity:
  def position: Position

trait MovingAbility:
  def updatePosition(position: Position) : VisibleEntity