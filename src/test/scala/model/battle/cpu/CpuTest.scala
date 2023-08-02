package model.battle.cpu

import model.battle.*
import model.battle.TrainerChoice.*
import model.battle.cpu.Cpu
import model.entities.World.Position
import model.entities.generator.PokemonGenerator
import model.entities.pokemon.*
import model.entities.pokemon.ElementType.*
import model.entities.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

import scala.language.postfixOps
class CpuTest extends AnyFlatSpec with should.Matchers:

  var bulbasaur: Pokemon = PokemonGenerator.getPokemonById("1").get
  var charmender: Pokemon = PokemonGenerator.getPokemonById("4").get
  val brazier: Move = Move(20, 20, "brazier", Fire , None)
  val action: Move = Move(20, 20, "action", Normal, None)
  val moves: Seq[Move] = Seq(action, action, brazier, action)
  bulbasaur = bulbasaur withMoves moves
  charmender= charmender withMoves moves

  "A Cpu " should " return a move in moves" in{
    Cpu(bulbasaur, charmender).optionChosen match
      case Attack(move) => println(move);assertResult(true)(moves.contains(move))
      case _ => true
  }

  "A Cpu " should " return eventually the super effective move " in{
    var passed = false
    for _ <- 1 to 100 do
      Cpu(bulbasaur, charmender).optionChosen match
        case Attack(move) if move == brazier => passed = true
        case _ =>
    assertResult(true)(passed)
  }