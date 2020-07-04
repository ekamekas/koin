package com.github.ekamekas.baha.common.ext

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.github.ekamekas.baha.common.BuildConfig
import com.github.ekamekas.baha.common.view.dialog.ActionPromptDialog
import com.github.ekamekas.baha.common.view.dialog.ProgressDialog

/**
 * Get DIP value from represented by integer value
 */
fun Context.dip(value: Int) = (value * resources.displayMetrics.density).toInt()
fun Context.dimens(@DimenRes id: Int) = resources.getDimension(id).toInt()
fun Context.drawable(@DrawableRes id: Int): Drawable = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) resources.getDrawable(id) else resources.getDrawable(id, null)

// dialog extension suite
fun Context.buildDialogActionPrompt(
    title: String,
    subtitle: String? = null,
    positiveButton: String? = null,
    negativeButton: String? = null,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: (() -> Unit)? = null
): Dialog {
    return ActionPromptDialog(
        context = this,
        title = title,
        subtitle = subtitle,
        positiveButton = positiveButton,
        negativeButton = negativeButton,
        onPositiveButtonClick = onPositiveButtonClick,
        onNegativeButtonClick = onNegativeButtonClick
    )
}
fun Context.dialogActionPrompt(
    title: String,
    subtitle: String? = null,
    positiveButton: String? = null,
    negativeButton: String? = null,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: (() -> Unit)? = null
) {
    buildDialogActionPrompt(
        title = title,
        subtitle = subtitle,
        positiveButton = positiveButton,
        negativeButton = negativeButton,
        onPositiveButtonClick = onPositiveButtonClick,
        onNegativeButtonClick = onNegativeButtonClick
    ).show()
}
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
