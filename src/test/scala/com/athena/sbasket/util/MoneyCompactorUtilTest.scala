package com.athena.sbasket.util

import org.scalatest.funsuite.AnyFunSuite
import MoneyUtil._

class MoneyCompactorUtilTest extends AnyFunSuite {

  test("Convert to coins") {
    val res = 0.2.toPounds

    assertResult("20p")(res)
  }

  test("Convert to coins 2") {
    val res = 0.21.toPounds

    assertResult("21p")(res)
  }

  test("Convert to pounds") {
    val res = 30.1.toPounds

    assertResult("£30.10")(res)
  }

  test("Convert to pounds 2") {
    val res = 3.13.toPounds

    assertResult("£3.13")(res)
  }

}
