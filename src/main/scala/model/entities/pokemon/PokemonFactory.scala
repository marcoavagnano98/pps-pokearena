package model.entities.pokemon

import model.parser.{MoveParser, PokedexParser}

import scala.util.Random

object PokemonFactory:
  private val listOfAllPokemon: Seq[Pokemon] = PokedexParser.getAllPokemon
  private val listOfAllMoves: Seq[Move] = MoveParser.getAllMoves
  private val random: Random = Random()
  private val numberOfMovesForPokemon = 4

  def apply(numberOfPokemon: Int) : Seq[Pokemon] =
    (1 to numberOfPokemon).foldLeft(Seq[Pokemon]())((p,i) => p :+ getRandomPokemon)

  private def getRandomPokemon: Pokemon =
    val moves = for
      i <- 0 until 4
      move = getRandomElement(listOfAllMoves,random)
    yield move
    val p = getRandomElement(listOfAllPokemon,random)
    p withMoves moves
  
  def getPokemonById(id: String): Option[Pokemon] = listOfAllPokemon.find(_.id == id)

  private def getRandomElement[A](seq: Seq[A], random: Random): A =
    seq(random.nextInt(seq.length))