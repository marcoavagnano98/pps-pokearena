package model.battle.cpu

import model.battle.TurnEvent
import model.entities.pokemon.{ComparatorTypeElement, Move, Pokemon}
import util.Utilities

import scala.language.postfixOps
import scala.util.Random

/** Represent the engine of [[Cpu]] responsible to choose the opponent option for the [[Battle]] */
trait Cpu:
  /**
   *
   * @return player pokemon
   */

  def playerPokemon: Pokemon

  /**
   *
   * @return opponent pokemon
   */

  def opponentPokemon: Pokemon

  /**
   *
   * @return the event chosen for that turn, there's 30 % of probability to chose the best move
   */

  def optionChosen: TurnEvent


object Cpu:
  def apply(playerPokemon: Pokemon, cpuPokemon: Pokemon): Cpu =
    CpuImpl(playerPokemon, cpuPokemon)

  private case class CpuImpl(override val playerPokemon: Pokemon, override val opponentPokemon: Pokemon) extends Cpu :
    private val movesMap: Map[Move, Double] = opponentPokemon.moves.filter(_.powerPoint > 0).map(move => (move -> 0.0)).toMap

    override def optionChosen: TurnEvent =
      import Utilities.dice
      TurnEvent.Attack(
        if Random.dice(30) then
          damageBonusScore(movesMap).toSeq.sortWith((x, y) => x._2 > y._2).map(t => t._1).head
        else
          val randomIndex = Random.between(0, 4)
            opponentPokemon
          .moves(randomIndex)
      )

    private def damageBonusScore(scoreMap: Map[Move, Double]): Map[Move, Double] =
      var updatedScoreMapMap: Map[Move, Double] = Map()
      for
        (move, score) <- scoreMap
        dbScore = ComparatorTypeElement(move.elementType, playerPokemon.elementType)
      do
        updatedScoreMapMap = updatedScoreMapMap + (move -> dbScore.+(score))
      updatedScoreMapMap