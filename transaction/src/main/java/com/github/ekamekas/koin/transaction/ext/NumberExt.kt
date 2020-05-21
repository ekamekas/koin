package com.github.ekamekas.koin.transaction.ext

import java.text.NumberFormat
import java.util.*

/**
 * Map double to currency string representation
 */
fun Double.toCurrency(): String {
    val currency = Currency.getInstance(Locale("in", "id"))
    return NumberFormat.getInstance().apply {
        this.maximumFractionDigits = 0
        this.currency = Currency.getInstance(Locale("in", "id"))
    }.format(this).let {
        currency.currencyCode.plus(" $it")
    }
}