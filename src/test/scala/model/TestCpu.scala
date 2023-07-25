import model.entities.{Item, ItemFactory, ItemId, Player, Trainer}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import model.entities.pokemon.*
import model.entities.pokemon.ElementType.{Normal, *}
import model.battle.*
import model.battle.TurnEvent.*
import model.battle.cpu.Cpu

import scala.language.postfixOps
import model.entities.World.Position
class TestCpu extends AnyFlatSpec with should.Matchers:

  var bulbasaur: Pokemon = PokemonFactory.getPokemonById("1").get
  var charmender: Pokemon = PokemonFactory.getPokemonById("4").get
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
    for i <- 1 to 100 do
      Cpu(bulbasaur, charmender).optionChosen match
        case Attack(move) if move == brazier => passed = true
        case _ =>
    assertResult(true)(passed)
  }