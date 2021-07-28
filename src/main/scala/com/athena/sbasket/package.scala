package com.athena

import java.time.LocalDate

package object sbasket {

  sealed trait Discount

  case class Product(name: String, price: Double, measure: String)

  case class ProductTotal(product: Product, count: Int, discount: Option[Discount])

  case class PercentageDiscount(
                                 percentsCoef: Double,
                                 from: Option[LocalDate] = None,
                                 to: Option[LocalDate] = None
                               ) extends Discount

  case class CountDiscount(
                            countByCondition: Int,
                            impactOnProductPrice: Double, // coefficient of discount changing (-50% = 0.5, -30%=0.3)
                            from: Option[LocalDate] = None,
                            to: Option[LocalDate] = None
                          ) extends Discount

  case class PriceCalculation(subtotal: Double, total: Double)

  case class Receipt(priceCalculation: PriceCalculation, discountNotes: List[String])

}
