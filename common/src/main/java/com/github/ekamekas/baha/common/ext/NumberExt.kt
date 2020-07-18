package com.github.ekamekas.baha.common.ext

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

/**
 * Map epoch to string representation
 */
fun Long.toDaysOfTheWeekOrDate(locale: String = "en", format: String = "dd MMM yyyy"): String {
    return when(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - this)) {
        -1L -> "Tomorrow"
        0L -> "Today"
        1L -> "Yesterday"
        else -> toDate(locale, format)
    }
}

/**
 * Map millis to epoch day
 */
fun Long.toEpochDay(): Long {
    return Calendar.getInstance().apply {
        timeInMillis = this@toEpochDay
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}