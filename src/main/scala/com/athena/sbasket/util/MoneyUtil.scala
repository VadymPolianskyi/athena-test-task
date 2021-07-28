package com.athena.sbasket.util

object MoneyUtil {

  private final val RoundNum = 2

  implicit class MoneyCompactor(price: Double) {
    def toPounds: String = {
      val rounded = price.round2

      if (rounded < 1) {
        val cut = rounded.toString.drop(2)
        if (cut.length < RoundNum) s"${cut}0p"
        else s"${cut}p"
      } else {
        val roundedStr = rounded.toString
        if (roundedStr.split('.')(1).length < RoundNum) s"£${roundedStr}0"
        else s"£$roundedStr"
      }
    }

    def round2: Double = {
      BigDecimal(price)
        .setScale(RoundNum, BigDecimal.RoundingMode.HALF_UP)
        .toDouble
    }
  }

}
