package view.battle.layout

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import model.entities.World.Position

abstract class BaseLayout[S](boundary: Rectangle, callback: S => Unit) extends Table:
  type T
  
  def update(newLayoutInfo: T) : Unit

  def listener(action: S): ClickListener = new ClickListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
      super.touchDown(event, x, y, pointer, button)
      callback(action)
      true
  }
  setSize(boundary.width, boundary.height)
  setPosition(boundary.x, boundary.y)
  
abstract class NoCallbackLayout(boundary: Rectangle) extends BaseLayout[Unit](boundary,  (_: Unit) => {})

