package model.battle

import model.battle.*
import model.entities.World.Position
import model.entities.generator.PokemonGenerator
import model.entities.pokemon.*
import model.entities.pokemon.ElementType.*
import model.entities.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{Status, *}

import scala.language.postfixOps
class BattleEngineTest extends AnyFlatSpec with should.Matchers:
  import TrainerChoice.*
  val actionMove: Move = Move(20, 10, "action", Normal, None)
  val lethalMove: Move = Move(9999, 10, "action", Normal, None)
  val bulbasaur: Pokemon = PokemonGenerator.getPokemonById("1").get withMoves Seq(actionMove,actionMove,actionMove,actionMove)
  val charmender: Pokemon = PokemonGenerator.getPokemonById("4").get withMoves Seq(actionMove,actionMove,actionMove,actionMove)

  val player: Player = Player(Position(0,0),"",PokemonGenerator(3))
  val opponent: Trainer = Trainer(Position(0,0),"",PokemonGenerator(3))
  val slowestTurn: Turn = Turn(player.id, bulbasaur, Attack(bulbasaur.moves.head))
  val fastestTurn: Turn = Turn(opponent.id,charmender, Attack(charmender.moves.head))

  "A BattleEngine " should " return the fastest Pokemon in battle first" in{
   BattleEngine(slowestTurn, fastestTurn).head.pokemon.id shouldBe charmender.id
  }
 
  "A BattleEngine " should " return damaged pokemon after attacks" in{
    val t1 = BattleEngine.turnAfterAttack(fastestTurn withTurnPerformed,slowestTurn,actionMove).swap
    val t2 = BattleEngine.turnAfterAttack(t1._1 withTurnPerformed, t1._2,actionMove).swap
    BattleEngine(slowestTurn, fastestTurn) shouldBe  Seq(t2._1, t2._2)
  }
  
  "A BattleEngine " should "return defeated pokemon with turn status Defeat" in{
    BattleEngine(fastestTurn, Turn(player.id, bulbasaur withHp 1, Attack(bulbasaur.moves.head)))(1).turnStatus shouldBe Status.Defeat
  }

  "A Battle engine " should " return healed battle units after hp recovery item used" in {
    val damagedBulbasaur: Pokemon = bulbasaur withHp 5
    val item: Item = ItemFactory(ItemType.SuperPotion) //Superpotion increase hp by 100
    val bagEvent = UseBag(item)
    val turnAfterHeal = BattleEngine.turnAfterHeal(Turn("", damagedBulbasaur, bagEvent), item)
    turnAfterHeal.pokemon.hp shouldBe damagedBulbasaur.maxHp //bulbasaur maxHp = 90
  }