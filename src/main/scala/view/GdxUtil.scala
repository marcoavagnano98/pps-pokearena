package view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
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

  /**
   *
   * @param path to the asset to use to generate a texture.
   * @return a [[Texture]] loaded by libGDX from the provided asset.
   */
  def texture (path:String) = new Texture(Gdx.files.classpath(path))


  object MemoHelper:
    /**
     * Memoize a function from I to O using a map.
     *
     * @param f a function that computes a value O from a key I.
     * @tparam I the key type.
     * @tparam O the value type.
     * @return a memoized version of f.
     */
    def memoize[I, O](f: I => O): I => O = new collection.mutable.HashMap[I, O]() :
      override def apply(key: I): O = getOrElseUpdate(key, f(key))