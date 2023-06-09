package model.parser

import model.entities.pokemon.{ElementType, Move, Pokemon, PokemonStatus}
import model.entities.pokemon.AllPokemonStatus.*

import scala.io.{Codec, Source}

object MoveParser:

  import io.circe.parser
  import io.circe.HCursor
  import io.circe.Decoder

  private val movesFileName = "data/moves.json"

  def getAllMoves: Seq[Move] =
    val inputString = Source.fromResource(movesFileName)(Codec("UTF-8")).mkString

    import PokedexParser.givenConversionStringElementType.given
    given pokemonDecoder: Decoder[Move] = (hCursor: HCursor) => {
      for {
        name <- hCursor.downField("name").as[String]
        element <- hCursor.downField("type").as[String]
        power <- hCursor.downField("power").as[Int]
        pp <- hCursor.downField("pp").as[Int]
        status <- hCursor.downField("effect").downField("statusCondition").as[Option[String]]
      } yield Move(damage = power, powerPoint = pp, name = name, elementType = element, status = convertStringToPokemonStatus(status))
    }

    val decodingResult = parser.decode[List[Move]](inputString)
    decodingResult match
      case Right(l) => l
      case _ => List()

  private def convertStringToPokemonStatus(status: Option[String]) : Option[PokemonStatus] = status match {
    case Some("paralysis") => Some(ParalyzeStatus())
    case Some("burn") => Some(BurnStatus())
    case _ => None
  }