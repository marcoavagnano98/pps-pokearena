package model.entities.pokemon

import model.entities.pokemon.AdditionalEffects.*
import model.entities.pokemon.Pokemon

trait PokemonStatus

trait StatusWithEffect extends PokemonStatus:
  type Result

  def applyStatus(p: Pokemon): Result

class HealthyStatus extends PokemonStatus

class ParalizedStatus extends StatusWithEffect with SkipTurn:
  override type Result = Boolean

  override def applyStatus(pokemon: Pokemon): Boolean =
    true

class BurnStatus extends StatusWithEffect with GainDamage:
  override type Result = Pokemon

  override def applyStatus(pokemon: Pokemon): Pokemon =
    pokemon withHp (pokemon.hp - damage)

object AdditionalEffects:
  trait SkipTurn:
    val probabilty = 30

  trait GainDamage:
    val damage = 30
