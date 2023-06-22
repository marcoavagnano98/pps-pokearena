package model.parser

import model.entities.pokemon.{ElementType, Move, Pokemon, PokemonStatus}
import model.entities.pokemon.BurnStatus
import model.entities.pokemon.ParalyzeStatus
import model.entities.pokemon.HealthyStatus

import scala.io.Source

object MoveParser:

  import io.circe.parser
  import io.circe.HCursor
  import io.circe.Decoder

  private val movesFileName = "move.json"

  def getAllMoves: Seq[Move] =
    val inputString = Source.fromResource(movesFileName).mkString

    import PokedexParser.givenConversionStringElementType.given
    given pokemonDecoder: Decoder[Move] = (hCursor: HCursor) => {
      for {
        name <- hCursor.downField("name").as[String]
        element <- hCursor.downField("type").as[String]
        power <- hCursor.downField("power").as[Int]
        pp <- hCursor.downField("pp").as[Int]
        status <- hCursor.downField("effect").downField("statusCondition").as[String]
      } yield Move(damage = power, powerPoint = pp, name = name, elementType = element, status = convertStringToPokemonStatus(status))
    }

    val decodingResult = parser.decode[List[Move]](inputString)
    decodingResult.toOption.get

  private def convertStringToPokemonStatus(status: String) : PokemonStatus = status match {
    case "paralysis" => ParalyzeStatus()
    case "burn" => BurnStatus()
    case _ => HealthyStatus()
  }
