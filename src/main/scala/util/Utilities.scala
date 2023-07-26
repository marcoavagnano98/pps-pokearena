package util
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.Timer.Task


import scala.util.Random

object Utilities:

  extension (r: Random)
    def dice(probability: Int): Boolean = r.between(1, 101) <= probability

  object GdxUtil:

    def scheduleDelayedAction(seconds: Int, action: => Unit) : Unit =
      Timer.schedule(new Task() {
        override def run(): Unit =
          action
      }, seconds)