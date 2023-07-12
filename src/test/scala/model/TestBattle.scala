package model

import model.entities.Player
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import model.entities.pokemon.*
import model.entities.pokemon.ElementType.*
import model.entities.Trainer
import model.battle.*

import scala.language.postfixOps
import model.entities.World.Position
class TestBattle extends AnyFlatSpec with should.Matchers:

  val bulbasaur: Pokemon = PokemonFactory.getPokemonById("1").get
  val charmender: Pokemon = PokemonFactory.getPokemonById("4").get
  val action: Move = Move(20, 10, "azione", Normal, None)
  val lethalMove: Move = Move(9999, 10, "azione", Normal, None)
  val player: Player = Player(Position(0,0),"",PokemonFactory(3))
  val opponent: Trainer = Trainer(Position(0,0),"",PokemonFactory(3))
  val slowestBt: BattleUnit = BattleUnit(bulbasaur, player, BattleOption.Attack(action))
  val fastestBt: BattleUnit = BattleUnit(charmender, opponent, BattleOption.Attack(action))

  "A BattleEngine " should " return the fastest Pokemon in battle first" in{
   BattleEngine(slowestBt, fastestBt).head.pokemon.id shouldBe charmender.id
  }
  "A BattleEngine " should " return damaged battle units after attacks" in{
    BattleEngine(slowestBt, fastestBt) shouldBe Seq(BattleEngine.unitAfterAttack(slowestBt, fastestBt,action), BattleEngine.unitAfterAttack(fastestBt, slowestBt, action))
  }
  val lethalBt: BattleUnit = BattleUnit(bulbasaur, player, BattleOption.Attack(lethalMove))

  "A BattleEngine " should " return only alive battle units" in{
    BattleEngine(lethalBt, fastestBt) shouldBe Seq(BattleEngine.unitAfterAttack(fastestBt,lethalBt ,action))
  }


