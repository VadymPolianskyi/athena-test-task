package com.athena.sbasket

import com.athena.sbasket.storage.Dao
import com.typesafe.scalalogging.StrictLogging

import scala.io.StdIn.readLine

trait Input[T] {
  def read(): List[Option[T]]
}

trait Output[T] {
  def print(rec: T): Unit
}

class ConsoleInputReader[T](storage: Dao[T]) extends Input[T] with StrictLogging{

  private final val Splitter = " "

  def read(): List[Option[T]] =
    preReadOrder().map(storage.get)
      .filter(_.isDefined)

  private def preReadOrder(): List[String] = {
    val inp: String = readLine()
    logger.debug(s"Read string: $inp")
    if (inp == "exit") {
      logger.info("Finishing program...")
      sys.exit()
    }

    filtered(inp.split(Splitter).toList)
  }

  private def filtered(orderList: List[String]): List[String] =
    orderList.tail

}

class ConsoleOutputWriter extends Output[Receipt] with StrictLogging {

  import util.MoneyUtil._

  override def print(receipt: Receipt): Unit = {

    val discounts = if (receipt.discountNotes.isEmpty) "(No offers available)"
    else receipt.discountNotes.mkString("\n")

    logger.info(
      s"""
         |Subtotal: ${receipt.priceCalculation.subtotal.toPounds}
         |$discounts
         |Total price: ${receipt.priceCalculation.total.toPounds}
         |""".stripMargin)
  }

}
