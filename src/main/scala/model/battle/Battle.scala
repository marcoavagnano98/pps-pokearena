package model.battle

import model.battle.cpu.Cpu
import model.entities.Trainer
import model.entities.pokemon.Pokemon
import model.entities.{Item, Player, Trainer}
import model.entities.pokemon.StatusEffects.{DealDamageEffect, SkipTurnEffect}
import model.entities.pokemon.ElementType.Fire
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
  def player: Player

  /**
   *
   * @return opponent [[Trainer]] of the battle
   */
  def opponent: Trainer

  /**
   *
   * @return a sequence of turn: [[Battle]] results
   */

  def playRound(playerChoice: TrainerChoice): Seq[Turn]

  /**
   *
   * @return optionally both [[Pokemon]] in [[Battle]] of [[player]] and opponent [[Trainer]]
   */
  def pokemonInBattle: (Option[Pokemon], Option[Pokemon])

object Battle:
  def apply(player: Player, opponent: Trainer): Battle =
    BattleImpl(player, opponent)

  private case class BattleImpl(override val player: Player,
                                override val opponent: Trainer,
                               ) extends Battle :

    import Status.*

    var playerTeam: Seq[Pokemon] = player.pokemonTeam
    var opponentTeam: Seq[Pokemon] = opponent.pokemonTeam

    override def playRound(playerChoice: TrainerChoice): Seq[Turn] =
      for turn <- BattleEngine(Turn(player.id, playerTeam.head, playerChoice),
        Turn(opponent.id, opponentTeam.head, Cpu(playerTeam.head, opponentTeam.head).optionChosen))
      yield {
        updatePokemonList(turn)
        turn
      }

    private def updatePokemonList(updatedUnit: Turn): Unit =
      updatedUnit.turnStatus match
        case Defeat if updatedUnit.trainerRef == player.id => playerTeam = playerTeam.tail
        case Defeat => opponentTeam = opponentTeam.tail
        case _ if updatedUnit.trainerRef == player.id => playerTeam = playerTeam updated(0, updatedUnit.pokemon)
        case _ => opponentTeam = opponentTeam updated(0, updatedUnit.pokemon)

    override def pokemonInBattle: (Option[Pokemon], Option[Pokemon]) =
      (playerTeam.headOption, opponentTeam.headOption)
