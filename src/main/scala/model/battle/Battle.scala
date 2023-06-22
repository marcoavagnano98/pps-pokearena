package model.battle

import model.entities.{Trainer}
import model.entities.pokemon.Pokemon
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
  def playerPokemon: List[Pokemon]
  /**
   *
   * @return list opponent's pokemon
   */
  def opponentPokemon: List[Pokemon]

  /**
   *
   * @return an instance of new [[Fight]]
   */
  def startFight: Fight


