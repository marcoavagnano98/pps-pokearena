package view

import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.Timer.Task

protected[view] object GdxUtil:
  extension (actor: Actor)
    def onTouchDown(action: => Unit): Unit =
      actor.addListener(new ClickListener() {
        override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean =
          super.touchDown(event, x, y, pointer, button)
          action
          true
      })

  def scheduleDelayedAction(seconds: Int, action: => Unit): Unit =
    Timer.schedule(new Task() {
      override def run(): Unit =
        action
    }, seconds)