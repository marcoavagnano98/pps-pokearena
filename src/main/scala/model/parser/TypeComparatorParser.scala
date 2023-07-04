package model.parser

import model.entities.pokemon.AllPokemonStatus.*
import model.entities.pokemon.{ElementType, Move, Pokemon, PokemonStatus}

import scala.collection.immutable.HashMap
import scala.io.Source

object TypeComparatorParser:

  import io.circe.{Decoder, HCursor, parser}

  private val typeCompareFileName = "data/typesCompare.json"

  def getAllTypesComparable: Seq[(String,String,Double)] =
    val inputString = Source.fromResource(typeCompareFileName).mkString
    print(inputString)

    given typesDecoder: Decoder[(String,String,Double)] = (hCursor: HCursor) => {
      for {
        firstType <-  hCursor.downField("attack").as[String]
        secondType <- hCursor.downField("defend").as[String]
        effectiveness <-  hCursor.downField("effectiveness").as[Double]

      } yield (firstType,secondType,effectiveness)
    }

    val decodingResult = parser.decode[List[(String,String,Double)]](inputString)
    println(decodingResult)
    decodingResult.toOption.get