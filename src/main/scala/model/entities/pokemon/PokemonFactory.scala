package model.entities.pokemon

import model.parser.{MoveParser, PokedexParser}

import scala.util.Random

object PokemonFactory:
  private val listOfAllPokemon: Seq[Pokemon] = PokedexParser.getAllPokemon
  private val listOfAllMoves: Seq[Move] = MoveParser.getAllMoves
  private val random: Random = Random()


  def getRandomPokemons(number: Int): Seq[Pokemon] =
    for
      i <- 0 until number
      p = getRandomPokemon
    yield
      p

  private def getRandomPokemon: Pokemon =
    val p = listOfAllPokemon(random.nextInt(listOfAllPokemon.length))
    val moves = for
      i <- 0 until 4
      move = listOfAllMoves(random.nextInt(listOfAllMoves.length))
    yield
      move
    p withMoves moves

  private def getPokemonById(id: String): Pokemon = listOfAllPokemon.filter(_.id == id).head