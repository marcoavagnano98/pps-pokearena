package model.entities
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import model.entities.World
import util.Drawable
import model.entities.World.Position
trait Map:
  def background: Texture
  def bounds: Rectangle
  def entities: List[VisibleEntity]

  def updateEntities(updatePos: Position): Map

object Map:
  def apply(path: String, x: Float, y: Float, width: Int, height: Int, entities: List[VisibleEntity]): Map =
    MapImpl(Texture(path), Rectangle(x, y, width, height), entities)

  private case class MapImpl(background: Texture, bounds: Rectangle, entities: List[VisibleEntity]) extends Map:
    def updateEntities(updatePos: Position): Map =
      val updatedEntities = entities.map {
        case player: Player => player.updatePosition(updatePos)
        case otherEntity => otherEntity
      }
      copy(entities = updatedEntities)
      

