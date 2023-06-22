package model.entities
import model.entities.pokemon.Pokemon

trait Trainer:
  /**
   * @return pokemonTeam return the pokemon team of the trainer
   *
   */
  def pokemonTeam: List[Pokemon]