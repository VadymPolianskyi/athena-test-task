package com.athena.sbasket.storage

import com.athena.sbasket.Product
import com.typesafe.scalalogging.StrictLogging

class ProductDao extends Dao[Product] with StrictLogging{

  import Dao.products

  def get(name: String): Option[Product] = {
    logger.debug(s"Getting Product by name '$name''")
    products.get(name)
  }
}
