package model.entities.generator

import model.entities.pokemon.{Move, Pokemon}
import model.parser.{MoveParser, PokedexParser}

import scala.util.Random

/**
 * Generator used to create Pokemon with built-in moves
 */
object PokemonGenerator:
  private val listOfAllPokemon: Seq[Pokemon] = PokedexParser.getAllPokemon
  private val listOfAllMoves: Seq[Move] = MoveParser.getAllMoves.filter(_.damage > 0)
  private val random: Random = Random()
  private val numberOfMovesForPokemon = 4

  def apply(numberOfPokemon: Int): Seq[Pokemon] =
    getRandomPokemon(listOfAllPokemon, numberOfPokemon)

  /**
   * @param id of the [[Pokemon]] you want to generate.
   * @return An [[Option]] of Some [[Pokemon]] if it exist otherwise an [[Option]] Empty
   */
  def getPokemonById(id: String): Option[Pokemon] = listOfAllPokemon.find(_.id == id).map(addMovesToPokemon(_, numberOfMovesForPokemon))

  /**
   * @param range           of the [[Pokemon]] based on his bst stats
   * @param numberOfPokemon the number of pokemon generated
   * @return A [[Seq]] of [[Pokemon]] based on [[range]] stats
   */
  def getPokemonByBstRange(range: (Int, Int), numberOfPokemon: Int): Seq[Pokemon] =
    getRandomPokemon(listOfAllPokemon.filter(p => p.bst >= range._1 && p.bst <= range._2), numberOfPokemon)

  /**
   * @param pokemonList     the list of [[Pokemon]] from which you want to pick
   * @param numberOfPokemon the number of pokemon u want to pick
   * @return A [[Seq]] of [[Pokemon]]
   */
  private def getRandomPokemon(pokemonList: Seq[Pokemon], numberOfPokemon: Int): Seq[Pokemon] =
    getNRandomElement(pokemonList, numberOfPokemon).map(addMovesToPokemon(_, numberOfMovesForPokemon))

  private def getNRandomElement[A](seq: Seq[A], numberOfElements: Int): Seq[A] = random.shuffle(seq).take(numberOfElements)

  private def addMovesToPokemon(pokemon: Pokemon, numberOfMoves: Int): Pokemon = pokemon withMoves getNRandomElement(listOfAllMoves, numberOfMoves)



