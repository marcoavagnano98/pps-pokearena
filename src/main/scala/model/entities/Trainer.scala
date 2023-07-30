package model.entities
import model.entities.pokemon.Pokemon
import com.badlogic.gdx.math.Rectangle
import model.entities.World.Position

trait Trainer extends VisibleEntity:
  def pokemonTeam: Seq[Pokemon]

object Trainer:
  def apply(pos: Position, id: String, pokemonList: Seq[Pokemon]): Trainer =
    TrainerImpl(pos, id, pokemonList)
  private case class TrainerImpl(override val position: Position, override val id: String, override val pokemonTeam: Seq[Pokemon]) extends Trainer

trait Player extends Trainer with MovingAbility:
  def withPokemon(pokemonTeam : Seq[Pokemon]) : Player
  def withPosition(position : Position) : Player
  def movesTo(direction: String): Player
  def bag: Bag

object Player:

  def apply(pos: Position, id: String, pokemonList: Seq[Pokemon]): Player =
    PlayerImpl(pos, id, pokemonList)
  private case class PlayerImpl(override val position: Position, override val id: String, override val pokemonTeam: Seq[Pokemon], override val bag: Bag = Bag()) extends Player:
    override def updatePosition(position: Position): Player = copy(position = position)
    override def withPokemon(pokemonTeam: Seq[Pokemon]): Player = copy(pokemonTeam=pokemonTeam)
    override def  withPosition(position : Position) : Player = copy(position=position)
    override def movesTo(direction: String): Player = copy(id=direction)