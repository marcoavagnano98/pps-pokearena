package model

import model.entities.pokemon.Pokemon
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import model.entities.{ItemFactory, Potion}
import model.entities.pokemon.ElementType
import model.entities.World.Position
import model.entities.ItemType
import model.entities.pokemon.AllPokemonStatus.{BurnStatus, HealthyStatus}
class ItemTest extends AnyFlatSpec with should.Matchers:

  private var bulbasaur = Pokemon("1","Bulbasaur",50,49,49,45,List(),ElementType.Grass)
  private val potion = ItemFactory(ItemType.Potion)
  private val superPotion = ItemFactory(ItemType.SuperPotion)
  private val antidote = ItemFactory(ItemType.Antidote)

  "A potion" should "increase the amount of hp of pokemon by 30" in {
    bulbasaur = bulbasaur withHp 30
    bulbasaur = potion use bulbasaur
    assertResult(50)(bulbasaur.hp)
  }

  "A superPotion" should "increase the amount of hp of pokemon by 50" in {
    bulbasaur = bulbasaur withHp 0
    bulbasaur = superPotion use bulbasaur
    assertResult(50)(bulbasaur.hp)
  }

  "A antidote" should "remove the current status of pokemon and change it into HealthyStatus" in {
    bulbasaur = bulbasaur withStatus BurnStatus()
    bulbasaur = antidote use bulbasaur
    assertResult(HealthyStatus())(bulbasaur.status)
  }

