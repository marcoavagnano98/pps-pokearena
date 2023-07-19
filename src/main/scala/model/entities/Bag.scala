package model.entities

trait Bag:
  def items : Seq[Item]
  def addItem(item: Item) : Unit
  def removeItem(item:Item) : Unit
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
