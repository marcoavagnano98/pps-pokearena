package view.battle.layout

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import model.entities.World.Position

abstract class BaseLayout[T, S](layoutData: T, boundary: Rectangle, actionPerformed: S => Unit) extends Table:
  
  def update(newLayoutInfo: T) : Unit
  
  def listener(action: S): ClickListener = new ClickListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
      super.touchDown(event, x, y, pointer, button)
      actionPerformed(action)
      true
  }

