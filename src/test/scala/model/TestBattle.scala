package model

import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import model.battle.FightTurn
import model.entities.pokemon.*
import model.entities.pokemon.ElementType.*

import scala.language.postfixOps

class TestBattle extends AnyFlatSpec with should.Matchers:
  val p1: Pokemon = Pokemon("a", hp = 100, attack = 100, defense = 100, speed = 100, moves = List(), elementType = Fire) withStatus ParalyzeStatus()
  val p2: Pokemon = Pokemon("b", hp = 100, attack = 100, defense = 100, speed = 101, moves = List(), elementType = Fire)
  val turn: FightTurn = FightTurn(Seq(p1, p2))
  "A FightTurn" should "has a list of a tuple where the opponent pokemon is first and player pokemon is last" in{
    assertResult(turn.generate)(Seq((p2,p1)))
  }

