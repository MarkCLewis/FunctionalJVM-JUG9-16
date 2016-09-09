package jugmeetup.scala

import io.StdIn._

object BLSUnemploymentExample extends App {
  case class StateInfo(number: Int, state: String) {
    def fileName = s"la.data.$number.$state"
  }
  case class Series(id: String, stype: Char, areaCode: String, srdCode: Int, title: String, beginYear: Int, beginPeriod: Int, endYear: Int, endPeriod: Int)
  case class Data(seriedID: String, year: Int, period: Int, value: Double)

  val mainURL = "http://download.bls.gov/pub/time.series/la/"
  val areasURL = mainURL+"la.state_region_division"
  val seriesURL = mainURL+"la.series"
  val dataRegex = """.*la\.data\.(\d+)\.(\w+).*""".r
  val dirLines = {
    val source = io.Source.fromURL(mainURL)
    val lines = source.getLines().toVector
    source.close
    lines.mkString.split("<br>").toVector
  }
  val stateFiles = for (dataRegex(n, s) <- dirLines; num = n.toInt; if num > 0) yield StateInfo(num, s)
  val srds = {
    val source = io.Source.fromURL(areasURL)
    val lines = source.getLines().drop(1).toVector
    source.close
    lines.map { line =>
      val p = line.split("\t").map(_.trim)
      p(0).toInt -> p(1)
    }
  }.toMap
  val series = {
    val source = io.Source.fromURL(seriesURL)
    val lines = source.getLines().drop(1).toVector
    source.close
    lines.map { line =>
      val p = line.split("\t").map(_.trim)
      Series(p(0), p(1)(0), p(2), p(5).toInt, p(6), p(8).toInt, p(9).drop(1).toInt, p(10).toInt, p(11).drop(1).toInt)
    }
  }

  println("Enter a regex to match the title of the data series you want to view.")

  def readKey(): Vector[Series] = {
    val keyword = readLine.r
    val matchedSeries = series.filter(s => keyword.findFirstIn(s.title).nonEmpty)
    if (matchedSeries.length > 30) {
      println("Too many matches. Try something else.")
      readKey()
    } else if (matchedSeries.isEmpty) {
      println("There were no matches. Try something else.")
      readKey()
    } else matchedSeries
  }
  val matchedSeries = readKey()
  println("Select a data series from below by number.")
  matchedSeries.zipWithIndex.foreach { t => println(t._1+". "+t._2) }
  val seriesNum = readInt
  val state = stateFiles.find(_.state == srds(matchedSeries(seriesNum).srdCode))
  println(state)
  state.foreach { s =>
    val dataURL = mainURL+"la.data."+s.number+"."+s.state
    val source = io.Source.fromURL(dataURL)
    val lines = source.getLines().drop(1)
    lines.map { line =>
      val p = line.split("\t").map(_.trim)
      Data(p(0), p(1).toInt, p(2).drop(1).toInt, p(3).toDouble)
    }.filter(_.seriedID == matchedSeries(seriesNum).id).foreach { d =>
      println(d)
    }
    source.close
  }
}