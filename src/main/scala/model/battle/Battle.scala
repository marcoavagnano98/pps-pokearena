package model.battle

import model.entities.Trainer
import model.entities.pokemon.Pokemon

import model.entities.{Opponent, Trainer}
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
  def player: Trainer

  /**
   *
   * @return opponent [[Trainer]] of the battle
   */
  def opponent: Trainer

  /**
   *
   * @return an instance of new [[BattleEngine]]
   */
  def fight(playerMove: Move, aiMove: Move): Boolean

enum BattleOption:
  case Attack(move: Move)
  case Bag(/*item*/)
  case Change(pos: Int)

case class BattleUnit(pokemon: Pokemon, trainer: Trainer, battleOption: BattleOption):
  def withPokemonUpdate(pokemon: Pokemon): BattleUnit = copy(pokemon = pokemon)

  def skipEffect: Boolean =
    pokemon.status match
      case s: SkipTurnEffect => s applyEffect pokemon
      case _ => false

  def withDamageStatusApplied: BattleUnit =
    pokemon.status match
      case s: DealDamageEffect => withPokemonUpdate(s.applyEffect(pokemon))
      case _ => this

  def withLife: Option[BattleUnit] =
    pokemon.hp match
      case value: Int if value > 0 => Some(copy())
      case _ => None

object Battle:
  def apply(player: Trainer, opponent: Trainer): Battle =
    BattleImpl(player, opponent)

  private case class BattleImpl(override val player: Trainer,
                                override val opponent: Trainer,
                               ) extends Battle :

    import util.Utilities.{pop, updatedHead}

    var playerPokemon: Seq[Pokemon] = player.pokemonTeam
    var opponentPokemon: Seq[Pokemon] = opponent.pokemonTeam

    override def fight(playerMove: Move, aiMove: Move): Boolean =
      (playerPokemon pop, opponentPokemon pop) match
        case (Some(p1), Some(p2)) =>
          playerPokemon = p1._2
          opponentPokemon = p2._2
          val playerUnit = BattleUnit(p1._1, player, BattleOption.Attack(playerMove))
          val opponentUnit = BattleUnit(p2._1, opponent, BattleOption.Attack(aiMove))
          for
            updatedUnit <- BattleEngine(playerUnit, opponentUnit)
          do updateDefenderDamage(updatedUnit)
          false

        case _ => println(playerPokemon); println(opponentPokemon); true

    def updateDefenderDamage(updatedUnit: BattleUnit): Unit = updatedUnit.trainer match
      case _: Opponent /*Player*/ => playerPokemon = playerPokemon updatedHead updatedUnit.pokemon
      case _ => opponentPokemon = opponentPokemon updatedHead updatedUnit.pokemon
