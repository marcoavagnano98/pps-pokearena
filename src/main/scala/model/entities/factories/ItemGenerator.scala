package model.entities.factories

import model.entities.{Item, ItemFactory}
import util.Grid
import scala.annotation.tailrec

/**
 * Factory used to construct Sequences of Items to be rendered on the Level screen
 */
object ItemGenerator:

  def apply(grid: Grid, numberOfItemsToGenerate: Int = 3): Seq[Item] =
    generateItems(grid, numberOfItemsToGenerate)

  private def generateItems(grid: Grid, numberOfItems: Int): Seq[Item] =
    @tailrec
    def _generateI(itemList: Seq[Item], numberOfItems: Int): Seq[Item] = itemList match
      case i if numberOfItems > 0 => _generateI(i :+ ItemFactory.getRandomItem(pos = grid.getRandomPos), numberOfItems - 1)
      case _ => itemList

    _generateI(Seq[Item](), numberOfItems)
