package model.entities.generator

import model.entities.{Item, ItemFactory, Trainer, VisibleEntity}

import scala.annotation.tailrec
import util.Grid

import scala.util.Random

/**
 * Generator used to create Sequences of Trainers to be rendered on the Level screen
 */
object TrainerGenerator:
  private val differentOpponentIdsNumber = 22
  private val idOpponent = "op"

  def apply(grid: Grid, numberOfTrainerToGenerate: Int = 3): Seq[Trainer] =
    generateTrainers(grid, numberOfTrainerToGenerate)

  private def generateTrainers(grid: Grid, numberOfTrainer: Int): Seq[Trainer] =
    @tailrec
    def _generateT(entityList: Seq[Trainer], numberOfTrainer: Int): Seq[Trainer] = entityList match
      case t if numberOfTrainer > 0 && grid.allAvailablePositions.nonEmpty =>
        _generateT(t :+ Trainer(id = idOpponent + Random.between(0, differentOpponentIdsNumber), pos = grid.getRandomPos, pokemonList = PokemonGenerator(2)), numberOfTrainer -1)
      case _ => entityList
    _generateT(Seq[Trainer](), numberOfTrainer)