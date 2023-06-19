package model.entities

trait BasePokemon extends Entity:
    type ElementType
    def hp: Int
    def attack: Int
    def defense: Int
    def speed: Int
    def moves: List[Move]

trait Move:
  type ElementType
  def damage: Int
  def powerPoint: Int
  def name: String