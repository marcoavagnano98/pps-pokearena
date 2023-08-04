package model.battle

import model.battle.{TrainerChoice, *}
import model.entities.World.Position
import model.entities.generator.PokemonGenerator
import model.entities.pokemon.*
import model.entities.pokemon.AllPokemonStatus.ParalyzeStatus
import model.entities.pokemon.ElementType.*
import model.entities.*
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.language.postfixOps
class TurnTest extends AnyFlatSpec with should.Matchers:
  val pokemon: Pokemon = PokemonGenerator(1).head
  private val thunderShock = Move(50,15,"thunderShock",ElementType.Fire,Some(ParalyzeStatus()))

  val turn: Turn = Turn("", pokemon, TrainerChoice.Attack(pokemon.moves.head))
  "A Turn not performed " should " return with performed false " in{
    assertResult(turn.performed)(false)
  }

  "A Turn performed " should " return with performed true " in{
    assertResult(turn.withTurnPerformed.performed)(true)
  }

  import model.battle.TurnStatus.*
  "A paralyzed Pokemon" should " eventually skip the Turn " in{
    var passed = false
    for _ <- 1 to 100 do
      val pTurn = Turn("", thunderShock applyStatus pokemon, TrainerChoice.Attack(pokemon.moves.head))
      if pTurn.checkSkipStatus.turnStatus == Skip then passed = true
    assertResult(true)(passed)
  }
