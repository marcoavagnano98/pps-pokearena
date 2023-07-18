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
  val actionMove: Move = Move(20, 10, "azione", Normal, None)
  val lethalMove: Move = Move(9999, 10, "azione", Normal, None)
  val player: Player = Player(Position(0,0),"",PokemonFactory(3))
  val opponent: Trainer = Trainer(Position(0,0),"",PokemonFactory(3))
  val slowestBt: BattleUnit = BattleUnit(player.id, bulbasaur, BattleOption.Attack(actionMove))
  val fastestBt: BattleUnit = BattleUnit(opponent.id,charmender, BattleOption.Attack(actionMove))

  "A BattleEngine " should " return the fastest Pokemon in battle first" in{
   BattleEngine(slowestBt, fastestBt).head.pokemon.id shouldBe charmender.id
  }

  "A BattleEngine " should " return damaged battle units after attacks" in{
    BattleEngine(slowestBt, fastestBt) shouldBe Seq(BattleEngine.unitAfterAttack(slowestBt, fastestBt,actionMove), BattleEngine.unitAfterAttack(fastestBt, slowestBt, actionMove))
  }
  val lethalBt: BattleUnit = BattleUnit(player.id, bulbasaur , BattleOption.Attack(lethalMove))

  "A BattleEngine " should " return only alive battle units" in{
    BattleEngine(lethalBt, fastestBt) shouldBe Seq(BattleEngine.unitAfterAttack(fastestBt,lethalBt ,actionMove))
  }

  "A Battle engine " should " return healed battle units after potion used" in {
    BattleEngine.unitAfterAttack(fastestBt, slowestBt, actionMove)
  }