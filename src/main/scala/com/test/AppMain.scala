package com.test

/*
object AppMain {

def main(args: Array[String]): Unit = {
  println(s"Source path ${args(0)}")

  println(s"Target path ${args(1)}")
}
*/

import scala.io.Source

object AppMain extends App {

  //  def main(args: Array[String]): Unit = {
  //
  //    "Source path"
  //    "Target path"

  println(s"Source path ${args(0)}")
  println(s"Target path ${args(1)}")

  def add(a:Int, b:Int): Int= {
    var c = a+b
    a+b+c
  }

  println(add(11,12))

  val divided = try {
    12/2
  }
  catch {
    case ae: ArithmeticException =>0
  }
  finally {
    println("Finally OK")
  }
  println(divided)

  var x = 0
  while (x<10) {
    println(s"Value x*x: ${x*x}")
    x += 1
  }

  x = 1
  var y = 1
  while (x <= 5) {
    y = 1
    while (y <= 5) {
      if ((x*y).toString.contains("4") || (x*y).toString.contains("6"))  {
        println(s"Value x*y: ${x * y}")
      }
      y += 1
    }
    x += 1
  }




  /*
  sealed trait Foo
  case class Bar(xs: Vector[String]) extends Foo
  case class Qux(i: Int, d: Option[Double]) extends Foo

      val foo: Foo = Qux(13, Some(14.0))
      val json = foo.asJson.noSpaces
      println(json)
    val decodedFoo = decode[Foo](json)
    println(decodedFoo)

    val source = Source.fromFile("src/main/resources/countries.json").mkString
    decode[Foo](source)

    printlm(source)

       */
}

