package model.battle

import model.entities.Trainer
import model.entities.pokemon.Pokemon

import model.entities.{Player, Trainer, Item}
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

  def takeTurn(playerChoice: BattleChoice, opponentChoice: BattleChoice): Unit
  
  /**
   *
   * @return optionally both pokemon in battle of player and opponent
   */
  def pokemonInBattle: (Option[Pokemon], Option[Pokemon])


enum BattleChoice:
  case Attack(move: Move)
  case Bag(item: Item)
  case Change(pos: Int)

case class BattleUnit(trainerRef: String,pokemon: Pokemon, battleOption: BattleChoice):
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
                               ) extends Battle:

    import util.Utilities.{pop, push, swap}
    import BattleChoice.*
    var playerTeam: Seq[Pokemon] = player.pokemonTeam
    var opponentTeam: Seq[Pokemon] = opponent.pokemonTeam
    
    override def takeTurn(playerChoice: BattleChoice, opponentChoice: BattleChoice): Unit =
      val t1 = selectPlayerPokemon(playerTeam,playerChoice)
      val t2 = opponentTeam.pop
      playerTeam = t1._2
      opponentTeam = t2._2
      for
        updatedUnit <- BattleEngine(BattleUnit(player.id,t1._1, playerChoice),  BattleUnit(opponent.id, t2._1, opponentChoice))
      do updatePokemonList(updatedUnit)
    
    def updatePokemonList(updatedUnit: BattleUnit): Unit =
        if updatedUnit.trainerRef == player.id then
          playerTeam = playerTeam push updatedUnit.pokemon
        else
          opponentTeam = opponentTeam push updatedUnit.pokemon
          
    def selectPlayerPokemon(pokemonList: Seq[Pokemon], battleChoice: BattleChoice): (Pokemon, Seq[Pokemon]) =
      battleChoice match
        case Change(pos) => pokemonList swap(0, pos) pop
        case _ =>  pokemonList pop
    
    override def pokemonInBattle: (Option[Pokemon], Option[Pokemon]) =
      (playerTeam.headOption, opponentTeam.headOption)