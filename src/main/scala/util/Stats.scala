package util

import model.entities.pokemon.Pokemon

trait Stats:

  def trainerDefeated: Int
  def levelRoomReached: Int
  def team: Seq[Pokemon]
  def bossDefeated: Boolean

  def updateStats(defeatedTrainer: Int, levelRoomReached: Int, a: Seq[Pokemon], bossDefeated: Boolean): Stats

object Stats:
  def apply(): Stats = StatsImpl()

  private case class StatsImpl(override val trainerDefeated: Int = 0, 
                               override val levelRoomReached: Int = 1,
                               override val team: Seq[Pokemon] = Seq.empty, 
                               override val bossDefeated: Boolean = false) extends Stats:

    override def updateStats(defeatedTrainer: Int, levelRoomReached: Int, pokemonTeam: Seq[Pokemon], bossDefeated: Boolean): Stats = 
      
      copy(trainerDefeated=defeatedTrainer, levelRoomReached=levelRoomReached, team=pokemonTeam, bossDefeated=bossDefeated)  