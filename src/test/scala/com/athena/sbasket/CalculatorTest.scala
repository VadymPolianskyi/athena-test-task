package com.athena.sbasket

import org.scalatest.funsuite.AnyFunSuite

class CalculatorTest extends AnyFunSuite {

  private val calculator = new Calculator()

  private val soupP = Product("Soup", 0.65, "tin")
  private val breadP = Product("Bread", 0.80, "loaf")
  private val milkP = Product("Milk", 1.30, "bottle")
  private val applesP = Product("Apples", 1.00, "bag")

  private val percentageDiscount = PercentageDiscount(0.1)
  private val countDiscount = CountDiscount(2, 0.5)

  test("Percentage discount with another goods") {
    val res = calculator.calculatePrice(List(
      ProductTotal(breadP, 1, Some(percentageDiscount)),
      ProductTotal(applesP, 1, None),
      ProductTotal(milkP, 2, None)))

    assertResult(PriceCalculation(4.4, 4.32))(res)
  }

  test("Count discount") {
    val res = calculator.calculatePrice(List(
      ProductTotal(breadP, 3, Some(countDiscount))
    ))

    assertResult(PriceCalculation(2.4, 2.0))(res)
  }

  test("Full pack") {
    val res = calculator.calculatePrice(List(
      ProductTotal(breadP, 30, Some(countDiscount)),
      ProductTotal(applesP, 10, Some(percentageDiscount)),
      ProductTotal(milkP, 90, None),
      ProductTotal(soupP, 90, Some(percentageDiscount))
    ))

    assertResult(PriceCalculation(209.5,198.65))(res)
  }

  test("Empty") {
    val res = calculator.calculatePrice(List())

    assertResult(PriceCalculation(0, 0))(res)
  }
}
