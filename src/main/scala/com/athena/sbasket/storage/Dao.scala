package com.athena.sbasket.storage

import com.athena.sbasket.{CountDiscount, Discount, PercentageDiscount, Product}

import java.time.LocalDate

trait Dao[T] {
  def get(k: String): Option[T]
}

object Dao {
  private[storage] lazy val products: Map[String, Product] = Map(
    "Soup" -> Product("Soup", 0.65, "tin"),
    "Bread" -> Product("Bread", 0.80, "loaf"),
    "Milk" -> Product("Milk", 1.30, "bottle"),
    "Apples" -> Product("Apples", 1.00, "bag"),
  )

  private[storage] lazy val discounts: Map[String, Discount] = Map(
    "Apples" -> PercentageDiscount(0.1, Some(LocalDate.parse("2021-07-27")), Some(LocalDate.parse("2021-08-03"))),
    "Bread" -> CountDiscount(2, 0.5),
  )
}
