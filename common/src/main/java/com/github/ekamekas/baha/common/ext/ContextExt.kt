package com.github.ekamekas.baha.common.ext

import android.content.Context
import android.widget.Toast

/**
 * Get DIP value from represented by integer value
 */
fun Context.dip(value: Int) = (value * resources.displayMetrics.density).toInt()

// Toast extension suite
fun Context.toastInfo(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
fun Context.toastSuccess(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
fun Context.toastError(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
fun Context.toastWarning(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}