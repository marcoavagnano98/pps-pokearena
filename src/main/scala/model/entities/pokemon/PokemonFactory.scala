package model.entities.pokemon

import model.parser.{MoveParser, PokedexParser}

import scala.util.Random

object PokemonFactory:
  private val listOfAllPokemon: Seq[Pokemon] = PokedexParser.getAllPokemon
  private val listOfAllMoves: Seq[Move] = MoveParser.getAllMoves
  private val random: Random = Random()
  private val numberOfMovesForPokemon = 4

  def getRandomPokemons(number: Int = 1): Seq[Pokemon] =
//    for
//      i <- List.from(number)
//      p = getRandomPokemon
//    yield p
    (1 to number).foldLeft(Seq[Pokemon]())((p,i) => p :+ getRandomPokemon)

  private def getRandomPokemon: Pokemon =
    val moves = for
      i <- 0 until 4
      move = getRandomElement(listOfAllMoves,random)
    yield move

    val p = getRandomElement(listOfAllPokemon,random)
    p withMoves moves

    //listOfAllPokemon(random.nextInt(listOfAllPokemon.length)) withMoves (1 to numberOfMovesForPokemon).foldLeft(Seq[Move]())((m,i) => m :+ listOfAllMoves(random.nextInt(listOfAllMoves.length)))

  def getPokemonById(id: String): Option[Pokemon] = listOfAllPokemon.find(_.id == id)

  private def getRandomElement[A](seq: Seq[A], random: Random): A =
    seq(random.nextInt(seq.length))