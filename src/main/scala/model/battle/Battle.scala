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

  def takeTurn(playerChoice: BattleAction): Pair[BattleUnit]

  /**
   *
   * @return optionally both pokemon in battle of player and opponent
   */
  def pokemonInBattle: (Option[Pokemon], Option[Pokemon])


enum BattleAction(val description: String):
  case Attack(move: Move) extends BattleAction("usa " + move.name)
  case Bag(item: Item) extends BattleAction("usa lo strumento " + item.name)
  case Change(pos: Int) extends BattleAction("viene sostituito")
  case SkippedTurn extends BattleAction("salta il turno")
  
case class BattleUnit(trainerRef: String, pokemon: Pokemon, battleOption: BattleAction):
  import BattleAction.*
  
  def withPokemonUpdate(pokemon: Pokemon): BattleUnit = copy(pokemon = pokemon)
  
  def skipEffect: Boolean =
    pokemon.status match
      case s: SkipTurnEffect => s applyEffect pokemon
      case _ => false
  
  def withTurnSkipped: BattleUnit =
    copy(battleOption = SkippedTurn)
    
  def withDamageStatusApplied: BattleUnit =
    pokemon.status match
      case s: DealDamageEffect if this stillInBattle => withPokemonUpdate(s.applyEffect(pokemon))
      case _ => this

  def stillInBattle: Boolean =
    pokemon.hp match
      case value: Int if value > 0 => true
      case _ => false

object Battle:
  def apply(player: Trainer, opponent: Trainer): Battle =
    BattleImpl(player, opponent)

  private case class BattleImpl(override val player: Trainer,
                                override val opponent: Trainer,
                               ) extends Battle :

    import util.Utilities.{pop, push, swap}
    import BattleAction.*

    var playerTeam: Seq[Pokemon] = player.pokemonTeam
    var opponentTeam: Seq[Pokemon] = opponent.pokemonTeam

    override def takeTurn(playerChoice: BattleAction): Pair[BattleUnit] =
      val t1 = selectPlayerPokemon(playerTeam, playerChoice)
      val t2 = opponentTeam.pop

      playerTeam = t1._2
      opponentTeam = t2._2
      val playerUnit = BattleUnit(player.id, t1._1, playerChoice)
      val opponentUnit = BattleUnit(opponent.id, t2._1, Cpu(t1._1, t2._1).optionChosen)

      var seqBattleUnit: Seq[BattleUnit] = BattleEngine(playerUnit, opponentUnit)
      if seqBattleUnit.head.trainerRef != player.id then
        seqBattleUnit = seqBattleUnit swap(0, 1)
      seqBattleUnit.foreach(updatePokemonList)
      BattlePair(seqBattleUnit)
    
    def updatePokemonList(updatedUnit: BattleUnit): Unit =
      if updatedUnit.trainerRef == player.id && updatedUnit.stillInBattle then
        playerTeam = playerTeam push updatedUnit.pokemon
      else if updatedUnit.stillInBattle then
        opponentTeam = opponentTeam push updatedUnit.pokemon


    def selectPlayerPokemon(pokemonList: Seq[Pokemon], battleChoice: BattleAction): (Pokemon, Seq[Pokemon]) =
      battleChoice match
        case Change(pos) => pokemonList swap(0, pos) pop
        case _ => pokemonList pop

    override def pokemonInBattle: (Option[Pokemon], Option[Pokemon]) =
      (playerTeam.headOption, opponentTeam.headOption)
