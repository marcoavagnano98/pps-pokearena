package model.parser

import model.entities.pokemon.AllPokemonStatus.*
import model.entities.pokemon.{ElementType, Move, Pokemon, PokemonStatus}

import scala.io.Source

object TypeComparatorParser:

  import io.circe.{Decoder, HCursor, parser}

  private val movesFileName = "data/move.json"

  def getAllTypesComparable: Map[ElementType,Map[ElementType,Double]] =
//    val inputString = Source.fromResource(movesFileName).mkString
//
//    import PokedexParser.givenConversionStringElementType.given
//    given pokemonDecoder: Decoder[Move] = (hCursor: HCursor) => {
//      for {
//        name <- hCursor.downField("name").as[String]
//        element <- hCursor.downField("type").as[String]
//        power <- hCursor.downField("power").as[Int]
//        pp <- hCursor.downField("pp").as[Int]
//        status <- hCursor.downField("effect").downField("statusCondition").as[String]
//      } yield Move(damage = power, powerPoint = pp, name = name, elementType = element, status = convertStringToPokemonStatus(status))
//    }
//
//    val decodingResult = parser.decode[List[Move]](inputString)
//    decodingResult.toOption.get
  ???
