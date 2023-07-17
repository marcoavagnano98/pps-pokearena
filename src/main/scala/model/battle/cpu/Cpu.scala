package model.battle.cpu

import model.battle.BattleChoice
import model.entities.pokemon.{ComparatorTypeElement, Move, Pokemon}

case class Cpu(playerPokemon: Pokemon, cpuPokemon: Pokemon):


  private val movesMap: Map[Move, Double] = cpuPokemon.moves.filter(_.powerPoint > 0).map(move => (move -> 0.0)).toMap

  def optionChosen: BattleChoice =
    BattleChoice.Attack(movesMap.toSeq.sortWith((x, y) => x._2 > y._2).map(t => t._1).head)

  private def damageBonusScore(scoreMap: Map[Move, Double]): Map[Move, Double] =
    var updatedScoreMapMap: Map[Move, Double] = Map()
      for
        (move, score) <- scoreMap
        dbScore = ComparatorTypeElement(move.elementType, playerPokemon.elementType)
      do
        updatedScoreMapMap = updatedScoreMapMap + (move -> dbScore.+(score))
      updatedScoreMapMap

