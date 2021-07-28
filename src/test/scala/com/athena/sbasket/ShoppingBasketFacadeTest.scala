package com.athena.sbasket

import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class ShoppingBasketFacadeTest extends AnyFunSuite  with MockFactory {
  private val discountServiceMock: DiscountService = stub[DiscountService]
  private val calculatorMock: Calculator = stub[Calculator]
  private val outputMock: Output[Receipt] = stub[ConsoleOutputWriter]

  private val soupP = Product("Soup", 0.65, "tin")
  private val breadP = Product("Bread", 0.80, "loaf")
  private val milkP = Product("Milk", 1.30, "bottle")
  private val applesP = Product("Apples", 1.00, "bag")

  private val percentageDiscount = PercentageDiscount(0.1)
  private val countDiscount = CountDiscount(2, 0.5)

  private val shoppingBasketFacade = new ShoppingBasketFacade(discountServiceMock, calculatorMock, outputMock)

  test("Apple, Mik and Bread") {
    val calculation = PriceCalculation(3.1, 3.0)
    (discountServiceMock.productDiscount _).when(applesP.name).returns(Some(percentageDiscount))
    (discountServiceMock.productDiscount _).when(breadP.name).returns(Some(countDiscount))
    (discountServiceMock.productDiscount _).when(milkP.name).returns(None)
    (calculatorMock.calculatePrice _).when(*).returns(calculation)


    val products = List(applesP, milkP, breadP)
    val resF = shoppingBasketFacade.countAndPrintCheck(products)

    val res = Await.result(resF, 10 seconds)

    val expected = Receipt(calculation, List("Apples 10% off: 10p"))
    assertResult(expected)(res)
  }

  test("More Breads") {
    val calculation = PriceCalculation(4.7, 4.2)
    (calculatorMock.calculatePrice _).when(*).returns(calculation)
    (discountServiceMock.productDiscount _).when(applesP.name).returns(Some(percentageDiscount))
    (discountServiceMock.productDiscount _).when(breadP.name).returns(Some(countDiscount))
    (discountServiceMock.productDiscount _).when(milkP.name).returns(None)
    (discountServiceMock.productDiscount _).when(soupP.name).returns(None)

    val products = List(applesP, milkP, soupP, breadP, breadP, breadP)
    val resF = shoppingBasketFacade.countAndPrintCheck(products)

    val res = Await.result(resF, 10 seconds)

    val expected = Receipt(calculation, List("Bread (for each 2 items) 50% off: 40p", "Apples 10% off: 10p"))
    assertResult(expected)(res)
  }
}
