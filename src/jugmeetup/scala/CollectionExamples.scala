package jugmeetup.scala

object CollectionExamples extends App {
  // Data from https://www.ssa.gov/oact/babynames/limits.html. National data set.
  case class BabyName(firstName: String, gender: Char, count: Int)

  def parseLine(line: String): BabyName = {
    val p = line.split(",")
    BabyName(p(0), p(1)(0), p(2).toInt)
  }

  val source = io.Source.fromFile("yob2015.txt")
  val allNames = source.getLines.map(parseLine).toVector
  source.close

  val commonNames = allNames.filter(_.count > 10000)
  val numberOfFemaleNames = allNames.count(_.gender == 'F')
  val numberOfMaleNames = allNames.count(_.gender == 'M')
  val numberOfFemaleBabies = allNames.filter(_.gender == 'F').map(_.count).sum
  val numberOfFemaleBabies2 = allNames.view.filter(_.gender == 'F').map(_.count).sum
  val numberOfFemaleBabies3 = allNames.foldLeft(0)((c, bn) => if (bn.gender == 'F') c + bn.count else c)
  
  val sortedByCount = Sorts.mergeSort(allNames.toList)(_.count < _.count)
  println(sortedByCount.takeRight(3))
  
  val nameCount = allNames.find(_.firstName == "Mark").map(_.count).getOrElse(-1)
}