package com.athena.sbasket.storage

import com.athena.sbasket.Discount
import com.typesafe.scalalogging.StrictLogging

class DiscountDao extends Dao[Discount] with StrictLogging{

  import Dao.discounts

  override def get(productName: String): Option[Discount] = {
    logger.debug(s"Getting Discount by productName '$productName''")
    discounts.get(productName)
  }
}