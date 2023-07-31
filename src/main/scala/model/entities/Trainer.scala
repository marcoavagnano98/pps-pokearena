package model.entities
import model.entities.pokemon.Pokemon
import com.badlogic.gdx.math.Rectangle
import model.entities.World.Position

/**
 * Represent the opponent in the Game
 */
trait Trainer extends VisibleEntity:
  /**
   *
   * @return the Pokemon team of the Trainer
   */
  def pokemonTeam: Seq[Pokemon]

object Trainer:
  def apply(pos: Position, id: String, pokemonList: Seq[Pokemon]): Trainer =
    TrainerImpl(pos, id, pokemonList)
  private case class TrainerImpl(override val position: Position, override val id: String, override val pokemonTeam: Seq[Pokemon]) extends Trainer

/**
 * Represents the entity controlled by the one whos playing
 */
trait Player extends Trainer with MovingAbility:
  /**
   * Udate the pokemon team of the Player
   * @param pokemonTeam the new Sequence of Pokemon representing the team of the Player
   * @return the Player updated
   */
  def withPokemon(pokemonTeam : Seq[Pokemon]) : Player

  /**
   * Update the position of the Player
   * @param position the new Position of the Player in the Level
   * @return the Player updated
   */
  def withPosition(position : Position) : Player

  /**
   * Update the image used to represent the Player on the screen
   * @param direction the new image of the Player
   * @return the Player updated
   */
  def movesTo(direction: String): Player

  /**
   * Retrive the information about the Bag of the Player
   * @return the Bag containing the Item collected by the Player
   */
  def bag: Bag

object Player:

  def apply(pos: Position, id: String, pokemonList: Seq[Pokemon]): Player =
    PlayerImpl(pos, id, pokemonList)
  private case class PlayerImpl(override val position: Position, override val id: String, override val pokemonTeam: Seq[Pokemon], override val bag: Bag = Bag()) extends Player:
    override def updatePosition(position: Position): Player = copy(position = position)
    override def withPokemon(pokemonTeam: Seq[Pokemon]): Player = copy(pokemonTeam=pokemonTeam)
    override def  withPosition(position : Position) : Player = copy(position=position)
    override def movesTo(direction: String): Player = copy(id=direction)