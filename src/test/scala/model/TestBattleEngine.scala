package model

import model.entities.{Item, ItemFactory, ItemId, Player, Trainer}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import model.entities.pokemon.*
import model.entities.pokemon.ElementType.*
import model.battle.*
import model.battle.TurnEvent.UseBag

import scala.language.postfixOps
import model.entities.World.Position
class TestBattleEngine extends AnyFlatSpec with should.Matchers:

  val bulbasaur: Pokemon = PokemonFactory.getPokemonById("1").get
  val charmender: Pokemon = PokemonFactory.getPokemonById("4").get
  val actionMove: Move = Move(20, 10, "action", Normal, None)
  val lethalMove: Move = Move(9999, 10, "action", Normal, None)
  val player: Player = Player(Position(0,0),"",PokemonFactory(3))
  val opponent: Trainer = Trainer(Position(0,0),"",PokemonFactory(3))
  val slowestBt: Turn = Turn(player.id, bulbasaur, TurnEvent.Attack(actionMove))
  val fastestBt: Turn = Turn(opponent.id,charmender, TurnEvent.Attack(actionMove))

  "A BattleEngine " should " return the fastest Pokemon in battle first" in{
   BattleEngine(slowestBt, fastestBt).head.pokemon.id shouldBe charmender.id
  }

  "A BattleEngine " should " return damaged battle units after attacks" in{
    BattleEngine(slowestBt, fastestBt) shouldBe Seq(BattleEngine.turnAfterAttack(slowestBt, fastestBt,actionMove), BattleEngine.turnAfterAttack(fastestBt, slowestBt, actionMove))
  }
  val lethalBt: Turn = Turn(player.id, bulbasaur , TurnEvent.Attack(lethalMove))

  "A BattleEngine " should "return defeated pokemon with turn event Defeat" in{
    BattleEngine(lethalBt, fastestBt).head.battleTurnEvent shouldBe TurnEvent.Defeat
  }

  "A Battle engine " should " return healed battle units after hp recovery item used" in {
    val damagedBulbasaur: Pokemon = bulbasaur withHp 5
    val item: Item = ItemFactory(ItemId.SuperPotion) //Superpotion increase hp by 50
    val bagEvent = UseBag(item)
    val turnAfterHeal = BattleEngine.turnAfterHeal(Turn("", damagedBulbasaur, bagEvent), item)
    turnAfterHeal.pokemon.hp shouldBe damagedBulbasaur.maxHp //bulbasaur maxHp = 45
  }