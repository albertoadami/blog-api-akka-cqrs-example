package it.adami.blog.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.util.Try

/** Utility class for working with string transformation from JSON requests
  */
object StringUtils {

  def parseDateTimeFromString(date: String): Try[LocalDate] = {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    Try {
      LocalDate.parse(date, formatter)
    }
  }

  def getDateFromString(date: String): LocalDate = {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    LocalDate.parse(date, formatter)
  }

  def isValidEmail(email: String): Boolean = {
    val ePattern =
      "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
    val p = java.util.regex.Pattern.compile(ePattern)
    val m = p.matcher(email)
    m.matches()
  }

}
