package model.battle

import model.entities.BasePokemon

/**
 * [[Fight]] models clash between two [[BasePokemon]]
 */
trait Fight:
  /**
   * 
   * @return player's [[BasePokemon]]
   */
  def playerPokemon: BasePokemon
  /**
   *
   * @return opponent's [[BasePokemon]]
   */
  def opponentPokemon: BasePokemon
  
  type Turn

  /**
   * 
   * @return defeated [[BasePokemon]]
   */
  def endFight: BasePokemon