package model.battle

import model.entities.pokemon.AdditionalEffects.{GainDamage, SkipTurn}
import model.entities.pokemon.Pokemon

/**
 * [[Fight]] models clash between two [[Pokemon]]
 */
trait Fight:
  /**
   * 
   * @return player's [[Pokemon]]
   */
  def playerPokemon: Pokemon
  /**
   *
   * @return opponent's [[Pokemon]]
   */
  def opponentPokemon: Pokemon
  
  type Turn

  /**
   * 
   * @return defeated [[Pokemon]]
   */
  def endFight: Pokemon

case class FightTurn(pokemonInBattle: Seq[Pokemon]):
  extension (seq: Seq[Pokemon])
    def missTurn: Seq[Pokemon] =
    /*seq.filter(p =>
      (p.status.isInstanceOf[SkipTurn] && !p.status.asInstanceOf[SkipTurn].applyStatus(p))
        || !p.status.isInstanceOf[SkipTurn])
  */
      seq.filter({
        case pokemon: Pokemon => pokemon.status match
          case s:SkipTurn => !s.applyStatus(pokemon)
          case _ => true
      })

    def applyStatusEffect : Seq[Pokemon] =
      seq.map(p => p.status match {
        case s: GainDamage => s.applyStatus(p)
        case _ => p
      })

    def bySpeed: Seq[Pokemon] =
      seq.sortWith(_.speed > _.speed)

  val generate:Seq[(Pokemon,Pokemon)] =
    for
      p <- pokemonInBattle.missTurn.applyStatusEffect.bySpeed
      p2 <- pokemonInBattle if !p.equals(p2)
    yield (p,p2)