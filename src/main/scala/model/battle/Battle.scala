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

  def takeTurn(playerChoice: BattleTurnEvent): (BattleUnit, BattleUnit)

  /**
   *
   * @return optionally both pokemon in battle of player and opponent
   */
  def pokemonInBattle: (Option[Pokemon], Option[Pokemon])


enum BattleTurnEvent(val description: String):
  case Attack(move: Move) extends BattleTurnEvent("usa " + move.name)
  case Bag(item: Item) extends BattleTurnEvent("usa lo strumento " + item.name)
  case Change(pos: Int) extends BattleTurnEvent("viene sostituito")
  case Skip extends BattleTurnEvent("salta il turno")
  case Defeat extends BattleTurnEvent("e' stato sconfitto")



object Battle:
  def apply(player: Trainer, opponent: Trainer): Battle =
    BattleImpl(player, opponent)

  private case class BattleImpl(override val player: Trainer,
                                override val opponent: Trainer,
                               ) extends Battle :

    import util.Utilities.swap
    import BattleTurnEvent.*

    var playerTeam: Seq[Pokemon] = player.pokemonTeam
    var opponentTeam: Seq[Pokemon] = opponent.pokemonTeam

    override def takeTurn(playerChoice: BattleTurnEvent): (BattleUnit, BattleUnit) =

      val playerUnit: BattleUnit = BattleUnit(player.id, playerTeam.head, playerChoice)
      val opponentUnit: BattleUnit = BattleUnit(opponent.id, opponentTeam.head, Cpu(playerTeam.head, opponentTeam.head).optionChosen)

      var seqBattleUnit: Seq[BattleUnit] = BattleEngine(playerUnit, opponentUnit)
      if seqBattleUnit.head.trainerRef != player.id then
        seqBattleUnit = seqBattleUnit swap(0, 1)
      seqBattleUnit.foreach(updatePokemonList)
      seqBattleUnit match
        case Seq(t1, t2) => (t1, t2)

    def updatePokemonList(updatedUnit: BattleUnit): Unit =
      updatedUnit.battleTurnEvent match
        case Defeat if updatedUnit.trainerRef == player.id => playerTeam = playerTeam.tail
        case Defeat => opponentTeam = opponentTeam.tail
        case _ if updatedUnit.trainerRef == player.id => playerTeam = playerTeam updated(0, updatedUnit.pokemon)
        case _ => opponentTeam = opponentTeam updated(0, updatedUnit.pokemon)

/*
    def selectPlayerPokemon(pokemonList: Seq[Pokemon], battleChoice: BattleTurnEvent): (Pokemon, Seq[Pokemon]) =
      battleChoice match
        case Change(pos) => pokemonList swap(0, pos)
        case _ => pokemonList*/

    override def pokemonInBattle: (Option[Pokemon], Option[Pokemon]) =
      (playerTeam.headOption, opponentTeam.headOption)
