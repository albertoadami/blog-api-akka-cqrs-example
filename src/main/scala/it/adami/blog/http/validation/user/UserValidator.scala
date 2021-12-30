package it.adami.blog.http.validation.user

import java.time.LocalDate
import java.util.Date

import cats.data.ValidatedNec
import it.adami.blog.http.validation.{
  DomainValidation,
  InvalidEmail,
  InvalidGender,
  InvalidPassword,
  IsEmpty
}
import it.adami.blog.util.StringUtils
import cats.implicits._

trait UserValidator {

  private def checkIfStringIsEmpty(string: String): Boolean = string.isEmpty

  type ValidationResult[A] = ValidatedNec[DomainValidation, A]

  def validateUserName(userName: String): ValidationResult[String] =
    if (checkIfStringIsEmpty(userName)) IsEmpty("username").invalidNec else userName.validNec

  def validateFirstName(firstName: String): ValidationResult[String] =
    if (checkIfStringIsEmpty(firstName)) IsEmpty("firstname").invalidNec else firstName.validNec

  def validateLastName(lastName: String): ValidationResult[String] =
    if (checkIfStringIsEmpty(lastName)) IsEmpty("lastname").invalidNec else lastName.validNec

  def validateBirthDate(birthDate: String): ValidationResult[LocalDate] = {
    StringUtils
      .parseDateTimeFromString(birthDate)
      .fold(
        _ => IsEmpty("dateOfBirth").invalidNec,
        _ => StringUtils.getDateFromString(birthDate).validNec
      )
  }

  def validateGender(gender: String): ValidationResult[String] = {
    val genderUp = gender.toUpperCase
    if (genderUp == "MALE" || genderUp == "FEMALE") genderUp.validNec else InvalidGender.invalidNec
  }

  def validateEmail(email: String): ValidationResult[String] =
    if (StringUtils.isValidEmail(email)) email.validNec else InvalidEmail.invalidNec

  def validatePassword(password: String): ValidationResult[String] =
    if (password.length < 8) InvalidPassword.invalidNec
    else password.validNec

}
