package jugmeetup.scala

object NumberedLines extends App {
  val regex = """(\d+)\.(.+)""".r
  val source = io.Source.fromFile("numberedLines.txt")
  val data = (for (regex(num, text) <- source.getLines) yield num.toInt -> text).toMap
  source.close
  println(data)
}