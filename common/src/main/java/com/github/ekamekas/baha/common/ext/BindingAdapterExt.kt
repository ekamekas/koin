package com.github.ekamekas.baha.common.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("toDate", "locale", requireAll = false)
fun TextView.setMillisToDate(millis: Long?, locale: String?) {
    text = (millis ?: System.currentTimeMillis()).let {
        if(locale != null) {
            it.toDate(locale)
        } else {
            it.toDate()
        }
    }
}