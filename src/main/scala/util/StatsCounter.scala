package util

import model.entities.{Entity, Item, Level, Trainer}

import scala.language.postfixOps


/** *
 * trainer battuti
 * livello raggiunto
 *
 */


object StatsCounter:

  private var storedData: Map[String, Int] = Map.empty

  private def mapper[T](elem: T): String =
    elem match
      case _: Trainer => "Allenatori sconfitti"
      case _: Item => "Strumenti raccolti"
      case _: Level => "Livelli superati"
      case _ => ""

  def apply(): Seq[(String, Int)] =
    val seq = storedData.toSeq
    reset()
    seq

  def reset(): Unit = storedData = Map.empty

  def count[T](elem: T): Unit =
    val elemMapped: String = mapper(elem)
    storedData += (elemMapped -> (storedData.getOrElse(elemMapped, 0) + 1))

@main
def main() =
  var l = List(10,20)
  val a = l
  l = List.empty[Int]

  println(a)
