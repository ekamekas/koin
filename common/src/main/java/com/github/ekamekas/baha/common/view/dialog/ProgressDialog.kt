package com.github.ekamekas.baha.common.view.dialog

import android.app.Dialog
import android.content.Context
import com.github.ekamekas.baha.common.R

/**
 * Process in progress dialog
 */
class ProgressDialog(context: Context): Dialog(context) {

    init {
        setContentView(R.layout.dialog_progress)
    }

}