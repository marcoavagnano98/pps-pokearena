package model.entities.generator

import model.entities.pokemon.{Move, Pokemon}
import model.parser.{MoveParser, PokedexParser}

import scala.util.Random

object PokemonGenerator:
  private val listOfAllPokemon: Seq[Pokemon] = PokedexParser.getAllPokemon
  private val listOfAllMoves: Seq[Move] = MoveParser.getAllMoves.filter(_.damage > 0)
  private val random: Random = Random()
  private val numberOfMovesForPokemon = 4

  def apply(numberOfPokemon: Int): Seq[Pokemon] =
    getRandomPokemon(numberOfPokemon)

  private def getRandomPokemon(numberOfPokemon: Int): Seq[Pokemon] =
    getNRandomElement(listOfAllPokemon, numberOfPokemon, random).map(p => p withMoves getNRandomElement(listOfAllMoves, numberOfMovesForPokemon, random))

  def getPokemonById(id: String): Option[Pokemon] = listOfAllPokemon.find(_.id == id)

  def getPokemonByBstRange(range: (Int, Int), numberOfPokemon: Int): Seq[Pokemon] =
    getNRandomElement(listOfAllPokemon.filter(p => p.bst >= range._1 && p.bst <= range._2), numberOfPokemon, random)

  private def getNRandomElement[A](seq: Seq[A], numberOfElements: Int, random: Random): Seq[A] =
    random.shuffle(seq).take(numberOfElements)