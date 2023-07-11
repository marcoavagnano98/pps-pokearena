package model.entities
import model.entities.pokemon.Pokemon
import com.badlogic.gdx.math.Rectangle
import model.entities.World.Position
import util.Drawable

trait Trainer extends VisibleEntity:
  def pokemonTeam: Seq[Pokemon]

object Trainer:
  def apply(x: Float, y: Float, id: String, pokemonList: Seq[Pokemon]): Trainer =
    TrainerImpl(Position(x, y), id, pokemonList)
  private case class TrainerImpl(override val position: Position, override val id: String, override val pokemonTeam: Seq[Pokemon]) extends Trainer

trait Player extends Trainer with MovingAbility

object Player:

  def apply(x: Float, y: Float, id: String, pokemonList: Seq[Pokemon]): Player =
    PlayerImpl(Position(x, y), id, pokemonList)
  private case class PlayerImpl(override val position: Position, override val id: String, override val pokemonTeam: Seq[Pokemon]) extends Player:
    override def updatePosition(position: Position): VisibleEntity = copy(position = position)

