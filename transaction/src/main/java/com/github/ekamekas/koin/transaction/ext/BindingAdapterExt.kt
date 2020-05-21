package com.github.ekamekas.koin.transaction.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("toCurrency")
fun TextView.setCurrencyText(double: Double) {
    text = double.toCurrency()
}