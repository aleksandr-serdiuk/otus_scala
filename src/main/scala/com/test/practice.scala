package com.test

import java.time.ZonedDateTime
import io.scalaland.chimney.dsl._
import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

import java.io.{BufferedReader, FileReader}
import java.util
import scala.util.{Failure, Success, Try, Using}
import collection.mutable._



object practice extends App {

  // parse deserialize
  object in {
    case class MakeCoffee(
                           id: Int,
                           kind: String,
                           addict: String)
  }

  object out {
    case class CoffeeMade(
                           id: Int,
                           kind: String,
                           forAddict: String,
                           at: ZonedDateTime)

  }

  import in._
  import out._


  val command = MakeCoffee(1, "RAF", "test")

  val coffeeMade = CoffeeMade(
    id = command.id,
    kind = command.kind,
    forAddict = command.addict,
    at = ZonedDateTime.now())

  val event = command.into[CoffeeMade]
    .withFieldComputed(_.at, _ => ZonedDateTime.now)
    .withFieldRenamed(_.addict, _.forAddict)
    .transform

  println(command)
  println(coffeeMade)
  println(event)

  // CoffeeMade(24, "Espresso", "Piotr", "2020-02-03T20:26:59.659647+07:00[Asia/Bangkok]")

  //  serialize
  case class Device(id: Int, model: String)
  case class User(name: String, devices: Seq[Device])

  implicit val codec: JsonValueCodec[User] = JsonCodecMaker.make
  val inputt =
    """
      |{
      |"name":"John",
      |"devices":[{"id":1,"model":"HTC One X"}]
      |}""".stripMargin

  val inputt2 =
    """
      |{
      |"name":"John",
      |"devices":[{"id":1,"model":"HTC One X"}]
      |},
      |{
      |"name":"Mike",
      |"devices":[{"id":2,"model":"iPhone"}]
      |}""".stripMargin

  //  val user = readFromArray(inputt.getBytes("UTF-8"))
  //  println(user)

  Try(readFromArray(inputt.getBytes("UTF-8"))) match {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }

  val function: User => Unit = user => println(s"User from res")
  Try(readFromArray(inputt.getBytes("UTF-8")))
    .map {
      function
    }
    .recover { case t: Throwable => println(t) }

  val ints: List[Int] = List(1, 2, 3, 4, 5).map(x => x + 5)
  val flatInts: List[Int] = List(1, 2, 3, 4, 5).flatMap(x => List(x + 5))
  val unit: Unit = List(1, 2, 3, 4, 5).foreach(x => println(x + 5))


  Option(555).foreach(x => println(x))

  val arrList = new util.ArrayList[String]()
  arrList.add("One")
  arrList.add("Two")
  arrList.add("Three")

  println(arrList)

  import scala.collection.JavaConverters._

  val jul: java.util.List[Int] = List(1, 2, 3).asJava
  val list: List[String] = arrList.asScala.toList

  // val json = writeToArray(User(name = "John", devices = Seq(Device(id = 2, model = "iPhone X"))))



  case class CountryLite(name: NameLite, area: Float, capital: List[String])
  case class NameLite(official: String)

  println(List(
    CountryLite(NameLite("Aruba"), 180.0F, List("Oranjestad")),
    CountryLite(NameLite("Islamic Republic of Afghanistan"), 652230.0F, List("Kabul")))
    .sortBy(x => x.area)(Ordering[Float].reverse).take(10))

}