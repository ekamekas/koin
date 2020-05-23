package com.github.ekamekas.baha.common.view.dialog

import android.content.Context
import com.github.ekamekas.baha.common.R
import com.github.ekamekas.baha.common.ext.visibleIf
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_action_prompt.*

/**
 * Prompt action dialog
 *
 * @param title dialog title
 * @param subtitle dialog subtitle, will be hidden if pass as null
 * @param positiveButton dialog positive button text, will use default if pass as null
 * @param negativeButton dialog negative button text, will use default if pass as null
 * @param onPositiveButtonClick dialog positive button click listener
 * @param onNegativeButtonClick dialog negative button click listener, will be hidden if pass as null
 */
class ActionPromptDialog(
    context: Context,
    private val title: String,
    private val subtitle: String? = null,
    private val positiveButton: String? = null,
    private val negativeButton: String? = null,
    private val onPositiveButtonClick: () -> Unit,
    private val onNegativeButtonClick: (() -> Unit)? = null
): BottomSheetDialog(context) {

    init {
        setContentView(R.layout.dialog_action_prompt)
        setupView()
    }

    private fun setupView() {
        tvTitle.apply {
            text = title
        }
        tvSubtitle.apply {
            subtitle?.also { text = it }
            visibleIf { subtitle != null }
        }
        btnPositive.apply {
            positiveButton?.also { text = it }
            setOnClickListener {
                dismiss()
                onPositiveButtonClick.invoke()
            }
        }
        btnNegative.apply {
            negativeButton?.also { text = it }
            setOnClickListener {
                dismiss()
                onNegativeButtonClick?.invoke()
            }
            visibleIf { onNegativeButtonClick != null }
        }
    }

}