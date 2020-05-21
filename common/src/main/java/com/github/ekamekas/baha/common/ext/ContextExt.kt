package com.github.ekamekas.baha.common.ext

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.github.ekamekas.baha.common.view.dialog.ProgressDialog

/**
 * Get DIP value from represented by integer value
 */
fun Context.dip(value: Int) = (value * resources.displayMetrics.density).toInt()

// dialog extension suite
fun Context.buildDialogProgress(): Dialog {
    return ProgressDialog(this)
}
fun Context.dialogProgress() {
    buildDialogProgress().show()
}

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
