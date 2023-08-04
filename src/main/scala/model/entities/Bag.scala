package model.entities

/**
 * Represents the Player's Bag, containing Items to be used during the Battle
 */
trait Bag:
  /**
   *
   * @return the Sequence of Item contained in the Bag
   */
  def items : Seq[Item]

  /**
   * Adds an Item to the Bag
   * @param item to be added
   */
  def addItem(item: Item) : Unit

  /**
   * Remove an Item from the Bag
   * @param item to be removed
   */
  def removeItem(item:Item) : Unit

  /**
   * Retrive an Item from the Bag
   * @param index represents the Item in the Bag to be retrieved
   * @return
   */
  def getItem(index: Int) : Item
  
object Bag:
  def apply() : Bag = BagImpl()

  private class BagImpl() extends Bag:
    private var _items: Seq[Item] = List.empty
    override def items: Seq[Item] = _items

    override def addItem(item: Item): Unit =
      _items = _items :+ item

    override def removeItem(item: Item): Unit =
      _items = _items.filter(_ != item)

    override def getItem(index: Int): Item =
      items(index)