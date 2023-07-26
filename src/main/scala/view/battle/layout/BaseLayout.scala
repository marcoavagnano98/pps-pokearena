package view.battle.layout

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Actor}
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import model.entities.World.Position

enum LayoutVisibility(val value: Boolean):
  case Visible extends LayoutVisibility(true)
  case NotVisible extends LayoutVisibility(false)
  
abstract class BaseLayout[S](boundary: Rectangle, callback: S => Unit) extends Table:
  type T
  
  def updateLayout(newLayoutInfo: T) : Unit

  def listener(action: S): ClickListener = new ClickListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
      super.touchDown(event, x, y, pointer, button)
      callback(action)
      true
  }
  setSize(boundary.width, boundary.height)
  setPosition(boundary.x, boundary.y)
  
  def updateActorByIndex(actor: Actor, i: Int): Unit = getCells.items(i).setActor(actor)

abstract class NoCallbackLayout(boundary: Rectangle) extends BaseLayout[Unit](boundary,  (_: Unit) => {})

