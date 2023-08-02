package controller
import model.entities.{Player, Trainer}
import model.entities.World.Position
import model.entities.generator.PokemonGenerator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers.must
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.shouldNot
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, BeforeAndAfterEach}

import scala.language.postfixOps

class BattleControllerTest extends AnyWordSpec with Matchers:

  val player: Player = Player(Position(0, 0), "player", Seq.empty)
  val opponent: Trainer = Trainer(id = "op", pos = Position(0, 0), pokemonList = Seq.empty)


  "BattleController " when {
    "battle never started yet " must  {
      "have null screen " in {
        BattleController.screen must be(null)
      }
      "have null model " in {
        BattleController.model must be(null)
      }
      "start battle is called " should{
        "create a battle model " in{
            BattleController.startBattle(player, opponent)
            BattleController.model should not be null
        }
      }
    }
  }
