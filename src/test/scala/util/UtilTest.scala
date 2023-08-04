package util

import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should
import org.scalatest.matchers.should.Matchers
import util.Utilities.*

import scala.language.postfixOps
import scala.util.Random

class UtilTest extends AnyWordSpec with should.Matchers:
  "Extension method dice " when {
    "receives a probability of 1, the event" should {
      "occur " in {
        assertResult(true)(Random.dice(100))
      }
    }
    "receives a probability of 0, the event" should{
      "not occur " in {
        assertResult(false)(Random.dice(0))
      }
    }
  }

  "Extension method toSeq taken a Tuple2 of Int " should {
    "return a sequence of Int" in{
      (1,2).toSeq shouldBe Seq(1,2)
    }
  }
  "Extension method toPair taken a Seq of Int != 2 " should {
    " return an empty Option" in {
      Seq(1, 2, 3).toPair shouldBe Option.empty
    }
  }

  "Extension method toPair taken a Seq of length = 2 " should {

    " return Option((Int, Int)) " in {
      Seq(1, 2).toPair shouldBe Option((1, 2))
    }
  }