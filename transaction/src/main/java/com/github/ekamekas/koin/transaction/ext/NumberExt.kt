package com.github.ekamekas.koin.transaction.ext

import java.text.NumberFormat
import java.util.*

/**
 * Map double to currency string representation
 */
fun Double.toCurrency(appendWithCode: Boolean = true): String {
    val currency = Currency.getInstance(Locale("in", "id"))
    return NumberFormat.getInstance().apply {
        this.maximumFractionDigits = 0
        this.currency = Currency.getInstance(Locale("in", "id"))
    }.format(this).let {
        if(appendWithCode) {
            currency.currencyCode.plus(" $it")
        } else {
            it
        }
    }
}