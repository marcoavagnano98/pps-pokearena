package model.entities

import model.entities.pokemon.{Pokemon, PokemonFactory}

import scala.annotation.tailrec

trait World:

  def player: Player
  //def player_=(player: Player): Unit
  def opponents: Seq[Trainer]
  def generate(pokemonTeam: Seq[Pokemon]): Unit


object World:
  def apply(): World = WorldImpl()

  class WorldImpl() extends World:
    private var _player: Player = Player(Position(0,0),"player",Seq.empty)
    private var _opponents: Seq[Trainer] = Seq.empty

    override def player = _player
    override def opponents = _opponents


    override def generate(pokemonTeam: Seq[Pokemon]): Unit =
      _player = _player withPokemon pokemonTeam
      _opponents = generateTrainer(3)

    //@tailrec
    private def generateTrainer(numberOfTrainer: Int): Seq[Trainer] =
      def _generate(trainerList: List[Trainer], numberOfTrainer: Int): Seq[Trainer] =
        trainerList match
          case l if numberOfTrainer>0 => _generate(l:+Trainer(id = "op"+numberOfTrainer, pos = randomPos(numberOfTrainer), pokemonList = PokemonFactory(2)), numberOfTrainer-1)
          case _ => trainerList
      _generate(List[Trainer](), numberOfTrainer)


    private def randomPos(x: Int): Position =
      Position(x, x)

  /**
   *  Position class represents the coordinates x,y in the World
   * @param x coordinate
   * @param y coordinate
   */
  case class Position(x: Double, y: Double)