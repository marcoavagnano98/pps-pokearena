package model.entities.pokemon

import model.entities.pokemon.ElementType.Normal
import model.parser.TypeComparatorParser


enum ElementType(val elemType:String):
  case Normal extends ElementType("normal")
  case Fire extends ElementType("fire")
  case Water extends ElementType("water")
  case Grass extends ElementType("grass")
  case Flying extends ElementType("flying")
  case Fighting extends ElementType("fighting")
  case Poison extends ElementType("poison")
  case Electric extends ElementType("electric")
  case Ground extends ElementType("ground")
  case Rock extends ElementType("rock")
  case Psychic extends ElementType("psychic")
  case Ice extends ElementType("ice")
  case Bug extends ElementType("bug")
  case Ghost extends ElementType("ghost")
  case Steel extends ElementType("steel")
  case Dragon extends ElementType("dragon")
  case Dark extends ElementType("dark")

object ComparatorTypeElement:
  private val allTypeComparator = TypeComparatorParser.getAllTypesComparable

  def apply(e1: ElementType,e2: ElementType) : Double =
    allTypeComparator.collectFirst({case (t1,t2,eff) if t1 == e1.elemType && t2 == e2.elemType => eff}).getOrElse(1)