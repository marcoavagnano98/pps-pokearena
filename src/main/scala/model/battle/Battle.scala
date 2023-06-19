package model.battle

import model.entities.{BasePokemon, Trainer}

/**
 * Battle models clash between two trainers
 */
trait Battle:
  /**
   *
   * @return player of the game of type [[Trainer]]
   */
  def player: Trainer

  /**
   *
   * @return opponent [[Trainer]] of the battle
   */
  def opponent: Trainer

  /**
   *
   * @return list player's pokemon
   */
  def playerPokemon: List[BasePokemon]
  /**
   *
   * @return list opponent's pokemon
   */
  def opponentPokemon: List[BasePokemon]

  /**
   *
   * @return an instance of new [[Fight]]
   */
  def startFight: Fight


