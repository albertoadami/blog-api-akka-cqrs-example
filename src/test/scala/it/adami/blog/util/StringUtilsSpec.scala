package it.adami.blog.util

import it.adami.blog.common.SpecBase

class StringUtilsSpec extends SpecBase {

  "StringUtils" must {
    "return true if a string is a valid email" in {
      StringUtils.isValidEmail("test@test.it") mustBe true
      StringUtils.isValidEmail("alberto_adami_1@domain123.it") mustBe true
    }

    "return false if a string is not a valid email" in {
      StringUtils.isValidEmail("test@test") mustBe false
      StringUtils.isValidEmail("invalid") mustBe false

    }
  }

}
