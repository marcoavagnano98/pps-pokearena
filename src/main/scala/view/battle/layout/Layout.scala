package view.battle.layout

trait Layout[T]:
  def layoutInfo: T
  
  def update(newLayoutInfo: T) : Unit