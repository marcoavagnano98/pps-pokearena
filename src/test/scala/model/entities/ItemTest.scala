package model.entities

import model.entities.World.Position
import model.entities.pokemon.{ElementType, Pokemon}
import model.entities.{ItemFactory, ItemType, Potion}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
class ItemTest extends AnyFlatSpec with should.Matchers:

  private var bulbasaur = Pokemon("1","Bulbasaur",50,49,49,45,List(),ElementType.Grass)
  private var giratina = Pokemon("2", "Giratina", 300, 150, 150, 100, List(), ElementType.Dragon)
  private val potion = ItemFactory(ItemType.Potion)
  private val superPotion = ItemFactory(ItemType.SuperPotion)

  "A potion" should "increase the Pokemon's HP by 50" in {
    giratina = giratina withHp 150
    giratina = potion use giratina
    assertResult(200)(giratina.hp)
  }

  "A superPotion" should "increase the Pokemon's HP by 100" in {
    giratina = giratina withHp 200
    giratina = superPotion use giratina
    assertResult(300)(giratina.hp)
  }

  "Using a potion on a fully healed pokemon" should "not change its HP" in {
    val initialHp = bulbasaur.hp
    bulbasaur = potion use bulbasaur
    bulbasaur.hp should be(initialHp)
  }

  "Using a superPotion on a fully healed pokemon" should "not change its hp" in {
    val initialHp2 = giratina.hp
    giratina = superPotion use giratina
    giratina.hp should be(initialHp2)
  }

  "Using multiple potions on a pokemon" should "increase its HP accordingly" in {
    giratina = giratina withHp 10
    for i <- 0 until 2 do
      giratina = potion use giratina
    giratina.hp should be(110)
  }

  "Using multiple SuperPotions on a Pokemon" should "increase its HP accordingly" in {
    giratina = giratina withHp 10
    for i <- 0 until 2 do
      giratina = superPotion use giratina
    giratina.hp should be(210)
  }
