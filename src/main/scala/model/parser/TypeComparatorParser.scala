package model.parser

import model.entities.pokemon.AllPokemonStatus.*
import model.entities.pokemon.{ElementType, Move, Pokemon, PokemonStatus}

import scala.collection.immutable.HashMap
import scala.io.Source
import io.circe.{Decoder, HCursor, parser}

object TypeComparatorParser:
  private val typeCompareFileName = "data/typesCompare.json"

  def getAllTypesComparable: Seq[(String,String,Double)] =
    val inputString = Source.fromResource(typeCompareFileName).mkString
    given typesDecoder: Decoder[(String,String,Double)] = (hCursor: HCursor) => {
      for {
        firstType <-  hCursor.downField("attack").as[String]
        secondType <- hCursor.downField("defend").as[String]
        effectiveness <-  hCursor.downField("effectiveness").as[Double]

      } yield (firstType,secondType,effectiveness)
    }

    val decodingResult = parser.decode[List[(String,String,Double)]](inputString)
    decodingResult match
      case Right(l) => l
      case _ => List()