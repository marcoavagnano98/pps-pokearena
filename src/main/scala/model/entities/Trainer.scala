package model.entities
import model.entities.BasePokemon

trait Trainer:
  /**
   * @return pokemonTeam return the pokemon team of the trainer
   *
   */
  def pokemonTeam: List[BasePokemon]