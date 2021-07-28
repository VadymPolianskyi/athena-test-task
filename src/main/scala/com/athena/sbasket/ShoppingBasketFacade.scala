package com.athena.sbasket


import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class ShoppingBasketFacade(discountService: DiscountService,
                           calculator: Calculator,
                           outputWriter: Output[Receipt]
                          ) extends StrictLogging {

  def countAndPrintCheck(goods: List[Product]): Future[Receipt] = {
    import com.athena.sbasket.util.ProductUtil._

    val productsTotals: List[ProductTotal] =
      goods.groupBy(_.name)
        .map {
          case (name, products) => ProductTotal(products.head, products.size, discountService.productDiscount(name))
        }.toList

    logger.debug(s"Converted Products to ProductTotal. Count: ${productsTotals.size}")

    val calculationsF = Future(calculator.calculatePrice(productsTotals))
    val discountNotesF = Future(productsTotals.map(_.convertToDiscountNote).filter(_.nonEmpty))

    val receipt = for {
      calculations <- calculationsF
      discountNotes <- discountNotesF
    } yield Receipt(calculations, discountNotes)

    receipt.onComplete {
      case Success(value) =>
        logger.info(s"Received Receipt: $value")
        outputWriter.print(value)
      case Failure(e) =>
        logger.error(s"Exception when calculated price for goods: $goods", e)
    }
    receipt
  }
}
