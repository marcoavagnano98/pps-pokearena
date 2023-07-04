package model.entities
import model.entities.pokemon.Pokemon
import com.badlogic.gdx.math.Rectangle
import model.entities.World.Position
import util.Drawable
trait Trainer extends Drawable
object Trainer:
  def apply(path: String, x: Float, y: Float, width: Float, height: Float): Trainer =
    TrainerImpl(path, Rectangle(x, y, width, height))
  private case class TrainerImpl(path: String, bounds: Rectangle) extends Trainer

trait Opponent extends Trainer with Drawable
  def pokemonTeam: List[Pokemon] = List.empty

object Opponent:
  def apply(path: String, x: Float, y: Float, width: Float, height: Float, pokemonTeam: List[Pokemon]): Opponent =
    OpponentImpl(path, Rectangle(x, y, width, height), pokemonTeam)
  private case class OpponentImpl(path: String, bounds: Rectangle, pokemonTeam: List[Pokemon]) extends Opponent
