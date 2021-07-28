package com.athena.sbasket.util

import com.athena.sbasket.util.MoneyUtil._
import com.athena.sbasket.{CountDiscount, PercentageDiscount, ProductTotal}

object ProductUtil {

  implicit class Discount(p: ProductTotal) {
    def applyDiscountAndCount: Double = p.discount match {
      case Some(PercentageDiscount(percentsCoef, _, _)) =>
        val total = p.product.price * p.count
        val discount = total * percentsCoef
        total - discount
      case Some(CountDiscount(countByCondition, impactOnProductPrice, _, _)) =>
        val productPrice = p.product.price
        if (p.count > countByCondition) {
          val countToDiscount = p.count / (countByCondition + 1)
          val discount = countToDiscount * (productPrice * impactOnProductPrice)
          productPrice * p.count - discount
        } else {
          productPrice * p.count
        }
      case None => p.product.price * p.count
    }

    def convertToDiscountNote: String = p.discount match {
      case Some(PercentageDiscount(percentsCoef, _, _)) =>
        val percents = percentsCoef * 100
        val discount = (p.product.price * p.count) * percentsCoef
        s"${p.product.name} ${percents.toInt}% off: ${discount.toPounds}"
      case Some(CountDiscount(countByCondition, impactOnProductPrice, _, _)) =>
        if (p.count > countByCondition) {
          val percents = impactOnProductPrice * 100
          val countToDiscount = p.count / (countByCondition + 1)
          val discount = countToDiscount * (p.product.price * impactOnProductPrice)

          s"${p.product.name} (for each $countByCondition items) ${percents.toInt}% off: ${discount.toPounds}"
        } else {
          ""
        }
      case None => ""

    }
  }

}
