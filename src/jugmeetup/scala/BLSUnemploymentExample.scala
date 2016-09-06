package jugmeetup.scala

object BLSUnemploymentExamples extends App {
  val mainURL = "http://download.bls.gov/pub/time.series/la/"
  val areasURL = mainURL + "la.area"
  val seriesURL = mainURL + "la.series"
  val dataRegex = """la\.data\.(\d+)\.(\w+)""".r
  val dirLines = {
    val source = io.Source.fromURL(mainURL)
    val lines = source.getLines().toVector
    source.close
    lines
  }
}