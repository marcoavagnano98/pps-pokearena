package prolog


import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfter
import org.scalatest.wordspec.AnyWordSpec
import pokearena.PokeArena
import prolog.PrologApp.BstGenerator

class PrologTest extends AnyWordSpec with BeforeAndAfter with Matchers:
  val nLevels:Int = 4
  val lowRange:Seq[(Int,Int)] = Seq((175,295),(200,320),(250,370),(400,520))
  val mediumRange:Seq[(Int,Int)] = Seq((275,395),(300,420),(350,470),(500,620))
  val hardRange:Seq[(Int,Int)] = Seq((375,495),(400,520),(450,570),(600,720))

  before{
    PrologApp()
  }

  "BSTGenerator for 4 levels " when{
    "receives 1 as difficulty " should{
      "generate a low BST range iterator " in{
        BstGenerator.generate(1, nLevels).toSeq shouldBe lowRange
      }
    }
    "receives 1 as difficulty " should{
      "generate a medium BST range iterator " in{
        BstGenerator.generate(2, nLevels).toSeq shouldBe mediumRange
      }
    }
    "receives 1 as difficulty " should{
      "generate an hard BST range iterator " in{
        BstGenerator.generate(3, nLevels).toSeq shouldBe hardRange
      }
    }
  }