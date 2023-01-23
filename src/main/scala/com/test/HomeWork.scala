package com.test

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonIgnoreProperties}
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.scala._
import com.fasterxml.jackson.databind.json.JsonMapper
import com.test.AppMain.args

import scala.io.Source
import java.nio.file.Paths

object HomeWork extends App {

  def isValidJSON(json: String) = {
    var valid = true
    val jacksonValid = new ObjectMapper with ScalaObjectMapper
    jacksonValid.registerModule(DefaultScalaModule)

    try {
      jacksonValid.readValue[List[Country]](json)
    } catch {
      case e: Exception =>
        valid = false
    }
    valid
  }

  val jackson = new ObjectMapper with ScalaObjectMapper
  jackson.registerModule(DefaultScalaModule)

  val source = Source.fromFile("src/main/resources/countries.json").mkString
  //val source = Source.fromURL("https://raw.githubusercontent.com/mledoze/countries/master/countries.json").mkString

  val target = if(args.length == 0) "src/main/resources/countries_res.json" else args(0)

  @JsonIgnoreProperties(ignoreUnknown = true)
  case class Country(name: CountryName, area: Float, capital: List[String], region: String)
  @JsonIgnoreProperties(ignoreUnknown = true)
  case class CountryName(official: String)

  case class AfricaResult(name: String, capital: String, area: Float)
  case class ErrorResult(error: String)

  if (isValidJSON(source)) {

    val resultSet = jackson.readValue[List[Country]](source).filter(Country => Country.region == "Africa").sortBy(Country => -Country.area).take(10)
    resultSet.foreach { country =>
      println(s"Country: ${country.name}; Capital: ${country.capital(0)}; Area: ${country.area}; Region: ${country.region}")
    }

    def genAfricaResult(country: Country): AfricaResult = {
      AfricaResult(
        name = country.name.official,
        capital = country.capital(0),
        area = country.area
      )
    }

    var listAfricaResult = List[AfricaResult]()
    resultSet.foreach { country =>
      listAfricaResult = listAfricaResult :+ genAfricaResult(country)
    }

    //jackson.writeValue(System.out, arrAfricaResult)
    jackson.writeValue(Paths.get(target).toFile(), listAfricaResult)
  }
  else
  {
    // jackson.writeValue(System.out, ErrorResult(error = "JSON error"))
    jackson.writeValue(Paths.get(target).toFile(), ErrorResult(error = "JSON error"))
  }

}
