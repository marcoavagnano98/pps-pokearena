package model.entities.pokemon

import model.entities.pokemon.Pokemon

trait PokemonStatus

trait PokemonStatusWithEffect extends PokemonStatus :
  type Result

  def applyStatus(p: Pokemon): Result

object AdditionalEffects:

  trait SkipTurn extends PokemonStatusWithEffect :
    override type Result = Boolean

    def probability: Int

  trait GainDamage extends PokemonStatusWithEffect :
    override type Result = Pokemon

    def damage: Int

import model.entities.pokemon.AdditionalEffects.*
import scala.util.Random
import util.Utilities.dice

class HealthyStatus extends PokemonStatus

class ParalyzeStatus extends PokemonStatusWithEffect with SkipTurn :
  override def probability: Int = 30

  override def applyStatus(pokemon: Pokemon): Boolean =
    Random.dice(probability)

class BurnStatus extends PokemonStatusWithEffect with GainDamage :
  override def damage: Int = 30

  override def applyStatus(pokemon: Pokemon): Pokemon =
    pokemon withHp (pokemon.hp - damage)