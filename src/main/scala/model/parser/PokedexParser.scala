package model.parser

import model.entities.pokemon.{ElementType, Move, Pokemon}

import scala.io.Source

object PokedexParser:

  import io.circe.parser
  import io.circe.HCursor
  import io.circe.Decoder

  private val pokedexFileName = "pokedex.json"

  def getAllPokemon: Seq[Pokemon] =
    val inputString = Source.fromResource(pokedexFileName).mkString

    import givenConversionStringElementType.given
    given pokemonDecoder: Decoder[Pokemon] = (hCursor: HCursor) => {
      for {
        name <- hCursor.downField("name").downField("english").as[String]
        elements <- hCursor.downField("type").as[Seq[String]]
        atk <- hCursor.downField("base").downField("Attack").as[Int]
        defense <- hCursor.downField("base").downField("Defense").as[Int]
        speed <- hCursor.downField("base").downField("Speed").as[Int]
        hp <- hCursor.downField("base").downField("HP").as[Int]
      } yield Pokemon(name, hp, atk, defense, speed, List[Move](), elements.head)
    }

    val decodingResult = parser.decode[List[Pokemon]](inputString)
    decodingResult.toOption.get

  object givenConversionStringElementType:
    given Conversion[String, ElementType] with
      override def apply(x: String): ElementType = ElementType.values.find(_.toString == x).get