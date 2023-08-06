package model.battle

import model.entities.Item
import model.entities.pokemon.{Move, Pokemon}
import model.entities.pokemon.StatusEffects.{DealDamageEffect, SkipTurnEffect}

import scala.language.postfixOps

enum TrainerChoice(val description: String, val priority: Int):
  case Attack(move: Move) extends TrainerChoice("use " + move.name, 0)
  case UseBag(item: Item) extends TrainerChoice("use " + item.name, 1)
/** Active and passive events that pokemon perform or suffers during the [[Turn]] */

enum TurnStatus(val description: Option[String]):
  case Skip extends TurnStatus(Some("skip the turn"))
  case Defeat extends TurnStatus(Some("was defeated"))
  case Alive extends TurnStatus(None)

/** Dynamic structure that maintains the information of pokemon during the [[Battle]] and keeps track of the events that happens during the [[Turn]] */
trait Turn:
  /**
   *
   * @return trainer id
   */
  def trainerRef: String

  /**
   *
   * @return pokemon in [[Turn]]
   */
  def pokemon: Pokemon
  /**
   *
   * @return true if [[Pokemon]] performs the [[Turn]]
   */
  def performed: Boolean
  /**
   *
   * @return [[TrainerChoice]] for current [[Turn]]
   */
  def trainerChoice: TrainerChoice

  /**
   *
   * @return [[TurnStatus]] of the [[Turn]]
   */
  def turnStatus: TurnStatus

  /**
   *
   * @param pokemon
   * @return a copy of [[Turn]] with pokemon update
   */
  def withPokemonUpdate(pokemon: Pokemon): Turn

  /**
   *
   *
   * @return copy of [[Turn]] with  [[turnStatus]] modified if the pokemon have to skip the turn
   */
  def checkSkipStatus: Turn

  /**
   *
   * @return copy of [[Turn]] with damage status applied
   */
  def withDamageStatusApplied: Turn

  /**
   *
   * @return copy of [[Turn]] with  [[turnStatus]] modified if the pokemon is defeated
   */
  def withDefeatChecked: Turn
  /**
   *
   * @return copy of [[Turn]] with flag performed True
   */
  def withTurnPerformed: Turn

object Turn:

  def apply(trainerRef: String, pokemon: Pokemon, option: TrainerChoice): Turn =
    TurnImpl(trainerRef: String, pokemon: Pokemon, option: TrainerChoice)

  private case class TurnImpl(override val trainerRef: String, override val pokemon: Pokemon,
                              override val trainerChoice: TrainerChoice, pf: Boolean = false, tStatus: TurnStatus = TurnStatus.Alive) extends Turn :

    import TurnStatus.*

    override def turnStatus: TurnStatus = tStatus

    override def performed: Boolean = pf

    override def withPokemonUpdate(pokemon: Pokemon): Turn =
      copy(pokemon = pokemon) withDefeatChecked

    override def checkSkipStatus: Turn =
      turnStatus match
        case Defeat => copy()
        case _ => pokemon.status match
          case s: SkipTurnEffect if s applyEffect pokemon => copy(tStatus = Skip)
          case _ => copy()

    override def withDamageStatusApplied: Turn =
      pokemon.status match
        case s: DealDamageEffect => withPokemonUpdate(s.applyEffect(pokemon)) withDefeatChecked
        case _ => copy()


    override def withDefeatChecked: Turn =
      pokemon.hp match
        case value: Int if value <= 0 => copy(tStatus = Defeat)
        case _ => copy()

    override def withTurnPerformed: Turn = copy(pf = true)