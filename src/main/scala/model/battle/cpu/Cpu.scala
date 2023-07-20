package model.battle.cpu

import model.battle.BattleTurnEvent
import model.entities.pokemon.{ComparatorTypeElement, Move, Pokemon}
import util.Utilities

import scala.language.postfixOps
import scala.util.Random

case class Cpu(playerPokemon: Pokemon, cpuPokemon: Pokemon):


  private val movesMap: Map[Move, Double] = cpuPokemon.moves.filter(_.powerPoint > 0).map(move => (move -> 0.0)).toMap
  /*
  * 30 % probability to use a best move against the player pokemon
  * */
  def optionChosen: BattleTurnEvent =
    import Utilities.dice
    
    BattleTurnEvent.Attack(
      if Random.dice(30) then
        damageBonusScore(movesMap).toSeq.sortWith((x, y) => x._2 > y._2).map(t => t._1).head
      else
        val randomIndex = Random.between(0,4)
        cpuPokemon.moves(randomIndex)
    )

  private def damageBonusScore(scoreMap: Map[Move, Double]): Map[Move, Double] =
    var updatedScoreMapMap: Map[Move, Double] = Map()
      for
        (move, score) <- scoreMap
        dbScore = ComparatorTypeElement(move.elementType, playerPokemon.elementType)
      do
        updatedScoreMapMap = updatedScoreMapMap + (move -> dbScore.+(score))
      updatedScoreMapMap