package model.battle

import model.entities.pokemon.Pokemon

/**
 * [[Fight]] models clash between two [[Pokemon]]
 */
trait Fight:
  /**
   * 
   * @return player's [[Pokemon]]
   */
  def playerPokemon: Pokemon
  /**
   *
   * @return opponent's [[Pokemon]]
   */
  def opponentPokemon: Pokemon
  
  type Turn

  /**
   * 
   * @return defeated [[Pokemon]]
   */
  def endFight: Pokemon