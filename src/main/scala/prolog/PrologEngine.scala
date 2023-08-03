package prolog

import alice.tuprolog.{Prolog, SolveInfo, Struct, Term, Theory}

import scala.io.Source

trait PrologEngine:
  /***
   *
   * @param difficulty the game difficulty
   * @param nLevels the total numbers of levels in game
   * @return [[Iterator]] of BST ranges for each level
   */
  def generateRange(difficulty: Int, nLevels: Int): Iterator[(Int, Int)]

  /***
   *
   * @param theory set prolog theory to engine
   */
  def setTheory(theory: Theory): Unit

object PrologEngine:
  /***
   *
   * @param defaultTheory initial prolog theory
   * @return [[PrologEngine]]
   */
  def apply(defaultTheory: Theory): PrologEngine = PrologEngineImpl(defaultTheory)

  private case class PrologEngineImpl(theory: Theory) extends PrologEngine:
    val engine: Prolog = Prolog()
    setTheory(theory)
    def setTheory(theory: Theory) : Unit =  engine.setTheory(theory)

    import PrologUtils.{*,given}

    given Conversion[String, Term] = Term.createTerm(_)


    def generateRange(difficulty: Int, nLevels: Int): Iterator[(Int, Int)] =
      val query: String = "generateLevels(" + difficulty + "," + nLevels + ",T)"
      solve(query).head

    /***
     *
     * @return solve a query and return a [[LazyList]] of [[SolveInfo]]
     */

    def solve: Term => LazyList[SolveInfo] = term => LazyList(engine solve term)/*
      goal =>
        new Iterable[SolveInfo] {

          override def iterator = new Iterator[SolveInfo] {
            val v0 = System.nanoTime()
            val v1 = System.nanoTime()
            println("Execution time: " + (v1 - v0) + "nanoseconds")
            var solution: Option[SolveInfo] = Some(engine.solve(goal))


            override def hasNext = solution.isDefined &&
              (solution.get.isSuccess || solution.get.hasOpenAlternatives)

            override def next() =
              try solution.get
              finally solution = if (solution.get.hasOpenAlternatives) Some(engine.solveNext()) else None
          }
        }.to(LazyList)*/

  /***
   * Contains givens and method used for converting [[Term]] to [[Iterator]]
   */
  object PrologUtils:
    private val rangeDefaultValue: (Int, Int) = (0, 600)

    given Conversion[String, Theory] = source => Theory.parseLazilyWithStandardOperators(Source.fromResource(source).mkString)

    given Conversion[SolveInfo, Iterator[(Int, Int)]] = info => {
     getRangeIterator(info.getTerm("T"))
    }

    private val stringToPair: String => (Int, Int) = s => s.split(",") match
      case Array(e1, e2) => try (e1.toFloat.toInt, e2.toFloat.toInt) catch case _: Exception => rangeDefaultValue
      case _ => rangeDefaultValue

    private def getRangeIterator(e: Term): Iterator[(Int, Int)] =


      val pattern = """\((.*?)\)""".r
      pattern
        .findAllIn(e.toString)
        .collect({
          case pattern(s) => s
        })
        .map(stringToPair(_))

