package model.entities

import model.entities.World.Position
import model.entities.pokemon.Pokemon
import model.entities.pokemon.StatusEffects.ChangeHpEffect

trait Item extends VisibleEntity:
  def name: String

  def use(p: Pokemon): Pokemon


case class Potion(override val id: String = "Item_01",override val position: Position, override val name: String="Potion") extends Item with ChangeHpEffect:
  override def hpToChange: Int = 50

  override def use(p: Pokemon): Pokemon =
    applyChangeStat(p)
    p
      
    
    
    
  
