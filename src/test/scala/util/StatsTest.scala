package util

import model.entities.*
import model.entities.World.Position
import model.entities.pokemon.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.Matchers

class StatsTest extends AnyFlatSpec with Matchers:
  private val stats = new Stats()

  "Stats" should "add elements to the map correctly" in {
    stats.count(Trainer(Position(0, 1), "op1", Seq()))
    stats.count(ItemFactory(ItemType.Potion))
    stats.count(GameStatus.Win)
    stats.count(GameStatus.Lose)
    stats.storedData should be(Map("item" -> 1, "trainer" -> 1, "win" -> 1, "lose" -> 1))
  }

  it should "increment the value correctly for existing elements" in {
    for i <- 1 to 3 do
      stats.count(ItemFactory.getRandomItem(Position(1,1)))
    stats.storedData should be(Map("item" -> 4, "trainer" -> 1, "win" -> 1, "lose" -> 1))
  }

  it should "reset the stored data when reset is called" in {
    stats.reset()
    stats.storedData should be(Map.empty)
  }