package com.athena.sbasket

import com.athena.sbasket.util.MoneyUtil._
import com.athena.sbasket.util.ProductUtil._
import com.typesafe.scalalogging.StrictLogging

class Calculator extends StrictLogging {

  def calculatePrice(goods: List[ProductTotal]): PriceCalculation = {
    val subtotal = goods.map(p => p.product.price * p.count).sum.round2
    val total = goods.map(p => p.applyDiscountAndCount).sum.round2
    logger.debug(s"Calculation finished with subtotal: $subtotal and total: $total")

    PriceCalculation(subtotal, total)
  }

}
