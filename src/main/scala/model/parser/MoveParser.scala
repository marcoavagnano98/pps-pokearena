package model.parser

import model.entities.pokemon.{AllPokemonStatus, ElementType, Move, Pokemon, PokemonStatus}
import model.entities.pokemon.AllPokemonStatus.*

import scala.io.{Codec, Source}
import io.circe.parser
import io.circe.HCursor
import io.circe.Decoder

/** A Parser that extract all move information from a local file*/
object MoveParser:
  private val movesFileName = "data/moves.json"

  /**
   * @return a list of all [[Move]] of the game
   */
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
    case Some(Status.Paralyze.name) => Some(ParalyzeStatus())
    case Some(Status.Freeze.name) => Some(FreezeStatus())
    case Some(Status.Poison.name) => Some(PoisonStatus())
    case Some(Status.Burn.name) => Some(BurnStatus())
    case _ => None
  }