package com.athena.sbasket

import com.athena.sbasket.storage.{Dao, DiscountDao}
import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite

import java.time.LocalDate

class DiscountServiceTest extends AnyFunSuite with MockFactory {

  val discountDaoMock: Dao[Discount] = stub[DiscountDao]
  val discountService = new DiscountService(discountDaoMock)

  private val productName = "Apples"

  private val percentageDiscount = PercentageDiscount(0.1)
  private val countDiscount = CountDiscount(2, 0.5)
  private val validPercentageDiscount = PercentageDiscount(0.1, Some(LocalDate.parse("2020-01-01")), Some(LocalDate.parse("2025-02-01")))
  private val invalidPercentageDiscount = PercentageDiscount(0.1, Some(LocalDate.parse("2020-01-01")), Some(LocalDate.parse("2020-02-01")))
  private val validCountDiscount = CountDiscount(2, 0.5, Some(LocalDate.parse("2020-01-01")), Some(LocalDate.parse("2025-02-01")))
  private val invalidCountDiscount = CountDiscount(2, 0.5, Some(LocalDate.parse("2020-01-01")), Some(LocalDate.parse("2020-02-01")))

  test("Get PercentageDiscount") {
    (discountDaoMock.get _).when(productName).returns(Some(percentageDiscount))

    val res = discountService.productDiscount(productName)
    assert(res.isDefined)
    assertResult(percentageDiscount)(res.get)
  }

  test("Get CountDiscount") {
    (discountDaoMock.get _).when(productName).returns(Some(countDiscount))

    val res = discountService.productDiscount(productName)
    assert(res.isDefined)
    assertResult(countDiscount)(res.get)
  }

  test("Get valid PercentageDiscount") {
    (discountDaoMock.get _).when(productName).returns(Some(validPercentageDiscount))

    val res = discountService.productDiscount(productName)
    assert(res.isDefined)
    assertResult(validPercentageDiscount)(res.get)
  }

  test("Get valid CountDiscount") {
    (discountDaoMock.get _).when(productName).returns(Some(validCountDiscount))

    val res = discountService.productDiscount(productName)
    assert(res.isDefined)
    assertResult(validCountDiscount)(res.get)
  }

  test("Get invalid PercentageDiscount") {
    (discountDaoMock.get _).when(productName).returns(Some(invalidPercentageDiscount))

    val res = discountService.productDiscount(productName)
    assert(res.isEmpty)
  }

  test("Get invalid CountDiscount") {
    (discountDaoMock.get _).when(productName).returns(Some(invalidCountDiscount))

    val res = discountService.productDiscount(productName)
    assert(res.isEmpty)
  }
}
