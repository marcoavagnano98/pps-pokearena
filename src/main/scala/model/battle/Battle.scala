package model.battle

import model.entities.Trainer
import model.entities.pokemon.{Move, Pokemon}

import scala.language.postfixOps
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
   * @return an instance of new [[Fight]]
   */
  def fight(playerMove: Move, aiMove:Move): Boolean

object Battle:
  def apply(player: Trainer, opponent: Trainer): Battle =
    BattleImpl(player,opponent)
  private case class BattleImpl(override val player: Trainer,
                                override val opponent: Trainer,
                                ) extends Battle:
    var playerPokemon:Seq[Pokemon] = player.pokemonTeam
    var opponentPokemon:Seq[Pokemon] = opponent.pokemonTeam

    override def fight(playerMove: Move, aiMove:Move): Boolean =
      import util.Utilities.pop
      (playerPokemon pop, opponentPokemon pop) match
        case (Some(p1), Some(p2)) =>
          playerPokemon = p1._2
          opponentPokemon = p2._2
          for
            updatedPokemon <- Fight(p1._1,p2._1,Map(p1._1.id -> playerMove, p2._1.id -> aiMove))
          do update(p1._1, p2._1, updatedPokemon)
          false

        case _ => true

    def update(p1:Pokemon, p2:Pokemon,updated: Pokemon): Unit =
      updated.id match
        case id: String if id == p1.id => playerPokemon = playerPokemon.updated(0, updated)
        case _ => opponentPokemon = opponentPokemon.updated(0, updated)





