package com.athena.sbasket

import com.athena.sbasket.storage.Dao
import com.typesafe.scalalogging.StrictLogging

import java.time.LocalDate

class DiscountService(storage: Dao[Discount]) extends StrictLogging {

  def productDiscount(productName: String): Option[Discount] = {
    logger.debug(s"Getting discount by productName '$productName'")
    storage.get(productName)
      .filter(checkDates)
  }

  private def checkDates(discount: Discount): Boolean = discount match {
    case PercentageDiscount(_, from, to) => checkDates(from, to)
    case CountDiscount(_, _, from, to) => checkDates(from, to)
    case _ => false
  }

  private def checkDates(fromO: Option[LocalDate], toO: Option[LocalDate]): Boolean = {
    if (fromO.isDefined) {
      val now = LocalDate.now()
      if (toO.isDefined) {
        (now.isAfter(fromO.get) || now.isEqual(fromO.get)) &&
          now.isBefore(toO.get)
      } else {
        now.isAfter(fromO.get)
      }
    } else {
      true
    }
  }
}
