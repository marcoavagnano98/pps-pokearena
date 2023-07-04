package model.entities
import com.badlogic.gdx.math.Rectangle
import util.Drawable

trait Map extends Drawable:

  /**
   *
   * @return the list of entities on the screen
   */
  def entities: List[VisibleEntity] = List.empty

  /**
   *
   * update the map on screen
   */
  def update: Unit = ???

object Map:
  def apply(path: String, x: Float, y: Float, width: Float, height: Float): Map =
    MapImpl(path, Rectangle(x, y, width, height))
  private case class MapImpl(path: String, bounds: Rectangle) extends Map

