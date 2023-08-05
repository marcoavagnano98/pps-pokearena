package model.parser

import model.entities.pokemon.{ElementType, Move, Pokemon}

import scala.io.{Codec, Source}
import io.circe.parser
import io.circe.HCursor
import io.circe.Decoder

/** A Parser that extract all pokemon information from a local file */
object PokedexParser:
  private val pokedexFileName = "data/pokedex.json"

  /**
   * @return a list of all [[Pokemon]] of the game
   */
  def getAllPokemon: Seq[Pokemon] =
    val inputString = Source.fromResource(pokedexFileName)(Codec("UTF-8")).mkString
    import givenConversionStringElementType.given
    given pokemonDecoder: Decoder[Pokemon] = (hCursor: HCursor) =>
      for
        id <- hCursor.downField("id").as[Int]
        name <- hCursor.downField("name").downField("english").as[String]
        elements <- hCursor.downField("type").as[Seq[String]]
        atk <- hCursor.downField("base").downField("Attack").as[Int]
        defense <- hCursor.downField("base").downField("Defense").as[Int]
        speed <- hCursor.downField("base").downField("Speed").as[Int]
        hp <- hCursor.downField("base").downField("HP").as[Int]
      yield Pokemon(id.toString, name, hp * 2, atk, defense, speed, List[Move](), elements.head)


    val decodingResult = parser.decode[List[Pokemon]](inputString)
    decodingResult match
      case Right(l) => l
      case _ => List()

  object givenConversionStringElementType:
    given Conversion[String, ElementType] with
      override def apply(x: String): ElementType = ElementType.values.find(_.toString.toLowerCase == x.toLowerCase).get