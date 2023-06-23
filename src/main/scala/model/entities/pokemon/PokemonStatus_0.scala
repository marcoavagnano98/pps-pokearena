//package model.entities.pokemon
//
//import model.entities.pokemon.Pokemon
//import util.Utilities.randomDice
//
//trait PokemonStatus
//
////trait PokemonStatusWithEffect extends PokemonStatus :
////  type Result
////
////  def probabilityForApplyStatus: Int
////
////  def applyStatus(p: Pokemon): Result
//
//trait PokemonStatusWithEffect extends PokemonStatus :
//  type Result
//
//  def probabilityForApplyStatus: Int
//
//  def applyStatus(p: Pokemon): Result
////  def applyStatus(p: Pokemon): Result =
////    if randomDice(probabilityForApplyStatus) then
////      p withStatus this
////    p
//
//object AdditionalEffects:
//
//  trait SkipTurn extends PokemonStatusWithEffect :
//    override type Result = Boolean
//
//    def probability: Int
//
//  trait GainDamage extends PokemonStatusWithEffect :
//    override type Result = Pokemon
//
//    def damage: Int
//
//  //  trait PersistentEffect:
//  //    def applyPersistentEffect(pokemon: Pokemon): Pokemon
//
//  trait PersistentEffect extends PokemonStatusWithEffect :
//    def applyPersistentEffect(pokemon: Pokemon): Pokemon
//    abstract override def applyStatus(p: Pokemon): Result = {super.applyStatus(applyPersistentEffect(p))}
//
//import model.entities.pokemon.AdditionalEffects.*
//import scala.util.Random
//import util.Utilities.*
//
//class HealthyStatus extends PokemonStatus
//
//class ParalyzeStatus extends PokemonStatusWithEffect with SkipTurn with PersistentEffect :
//  private val decreaseSpeedByPercentage = 50
//
//  override def probabilityForApplyStatus: Int = 30
//
//  override def probability: Int = 30
//
//  override def applyStatus(p: Pokemon): Result =
//    val newP = applyPersistentEffect(p)
//    if randomDice(probabilityForApplyStatus) then
//      newP withStatus this
//    newP
//
//  override def applyPersistentEffect(pokemon: Pokemon): Pokemon =
//    pokemon withSpeed (pokemon.speed * decreaseSpeedByPercentage / 100)
//
//
//class BurnStatus extends PokemonStatusWithEffect with GainDamage :
//  override def damage: Int = 30
//
//  override def probabilityForApplyStatus: Int = 30
//
//  override def applyStatus(pokemon: Pokemon): Pokemon =
//    pokemon withHp (pokemon.hp - damage)