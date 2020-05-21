package com.github.ekamekas.baha.common.ext

import java.text.SimpleDateFormat
import java.util.*

/**
 * Map epoch to string representation
 */
fun Long.toTime(locale: String = "en", format: String = "HH:mm"): String {
    return SimpleDateFormat(format, Locale(locale))
        .format(Date(this))
}

/**
 * Map epoch to string representation
 */
fun Long.toDate(locale: String = "en", format: String = "dd MMM yyyy"): String {
    return SimpleDateFormat(format, Locale(locale))
        .format(Date(this))
}