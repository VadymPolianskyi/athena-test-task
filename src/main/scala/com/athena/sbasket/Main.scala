package com.athena.sbasket

import com.athena.sbasket.storage.{Dao, DiscountDao, ProductDao}
import com.typesafe.scalalogging.{Logger, StrictLogging}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object Main extends StrictLogging{

  def main(args: Array[String]): Unit = {
    logger.info("Starting Shopping Basket Service...")
    val productStorage: Dao[Product] = new ProductDao()
    val discountStorage: Dao[Discount] = new DiscountDao()

    val calculator: Calculator = new Calculator
    val discountService: DiscountService = new DiscountService(discountStorage)
    val input: Input[Product] = new ConsoleInputReader(productStorage)
    val output: Output[Receipt]  = new ConsoleOutputWriter
    val shoppingBasket = new ShoppingBasketFacade(discountService, calculator, output)

    while (true) {
      val products = input.read().filter(_.isDefined).map(_.get)
      Await.ready(shoppingBasket.countAndPrintCheck(products), 10 seconds)
      logger.info("Calculation finished")
    }
  }
}
