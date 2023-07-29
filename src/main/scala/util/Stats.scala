package util

import model.entities.{Door, GameStatus, Item, Trainer}
import model.entities.GameStatus.*

class Stats():
  private var _storedData: Map[String, Int] = Map.empty
  def storedData: Map[String, Int] = _storedData

  def reset(): Unit = _storedData = Map.empty

  def count[E](elem: E): Unit =
    val e = converterToKeyString(elem)
    _storedData += (e -> (_storedData.getOrElse(e, 0) + 1))

  private def converterToKeyString[E](e: E): String =
    e match
      case _: Trainer => "trainer"
      case _: Item => "item"
      case Win => "win"
      case Lose => "lose"
      case _  => ""