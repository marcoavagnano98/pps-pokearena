package util

import model.entities.{Door, GameStatus, Item, Trainer}
import model.entities.GameStatus.*

/**
 * Class that represents game statistics. It maintains a [[Map]][String, Int] where information about defeated [[Trainer]],
 * collected [[Item]], and whether the game was won or lost are stored
 */
class Stats():
  private var _storedData: Map[String, Int] = Map.empty

  /**
   *
   * @return the [[Map]] of stored information
   */
  def storedData: Map[String, Int] = _storedData

  /**
   * clear the information contained in the Map
   */
  def reset(): Unit = _storedData = Map.empty

  /**
   * Insert the input element into the [[Map]]. If the element is already present, increase the associated value by 1;
   * otherwise, insert it with a value of 0."
   * @param elem the element to be added in the Map
   * @tparam E the type of the element to be added: should be [[Trainer]], [[Item]], Win or Lose.
   */
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