package jugmeetup.scala

import scala.annotation.tailrec

object Sorts extends App {
  @tailrec
  private def merge[A](lst1: List[A], lst2: List[A], merged: List[A])(lt: (A, A) => Boolean): List[A] =
    (lst1, lst2) match {
      case (_, Nil) => (lst1.reverse ::: merged).reverse
      case (Nil, _) => (lst2.reverse ::: merged).reverse
      case (h1 :: t1, h2 :: t2) => if (lt(h1, h2)) merge(t1, lst2, h1 :: merged)(lt) else merge(lst1, t2, h2 :: merged)(lt)
    }

  private def merge[A](lst1: List[A], lst2: List[A])(lt: (A, A) => Boolean): List[A] =
    (lst1, lst2) match {
      case (_, Nil) => lst1
      case (Nil, _) => lst2
      case (h1 :: t1, h2 :: t2) => if (lt(h1, h2)) h1 :: merge(t1, lst2)(lt) else h2 :: merge(lst1, t2)(lt)
    }

  def mergeSort[A](lst: List[A])(lt: (A, A) => Boolean): List[A] = lst match {
    case Nil => Nil
    case _ :: Nil => lst
    case _ =>
      val (left, right) = lst.splitAt(lst.length/2)
      merge(mergeSort(left)(lt), mergeSort(right)(lt), Nil)(lt)
  }
  
  println(mergeSort(List.fill(10)(math.random))(_ < _))
}