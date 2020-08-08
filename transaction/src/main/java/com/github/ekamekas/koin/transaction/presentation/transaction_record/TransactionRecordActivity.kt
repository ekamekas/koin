package com.github.ekamekas.koin.transaction.presentation.transaction_record

import android.app.Activity
import androidx.viewpager2.widget.ViewPager2
import com.github.ekamekas.baha.common.ext.buildDialogActionPrompt
import com.github.ekamekas.baha.common.ext.buildDialogProgress
import com.github.ekamekas.baha.common.ext.exhaustive
import com.github.ekamekas.baha.common.ext.toastError
import com.github.ekamekas.baha.core.presentation.activity.BaseActivityDataBinding
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.baha.core.presentation.view_object.StateObserver
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.ActivityTransactionRecordBinding
import com.github.ekamekas.koin.transaction.presentation.ExtraCode
import com.github.ekamekas.koin.transaction.presentation.view_object.TransactionRecordVO
import com.github.ekamekas.koin.transaction.presentation.view_object.toDomain

/**
 * Activity to create, update, and view transaction record
 */
class TransactionRecordActivity: BaseActivityDataBinding<TransactionRecordViewModel, ActivityTransactionRecordBinding>() {

    // dialog
    private val actionPromptDialog by lazy {
        buildDialogActionPrompt(
            title = getString(R.string.hint_delete_operation_prompt),
            onPositiveButtonClick = {
                viewModel.onTransactionRecordDelete()
            },
            onNegativeButtonClick = {/*nop*/}
        )
    }
    private val progressDialog by lazy {
        buildDialogProgress()
    }

    override val viewModelClazz: Class<TransactionRecordViewModel>
        get() = TransactionRecordViewModel::class.java
    override val resourceId: Int
        get() = R.layout.activity_transaction_record

    override fun setupBinding() {
        dataBinding.viewModel = viewModel
    }

    override fun setupObservers() {
        viewModel.onTransactionRecordAddEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Success -> {
                    progressDialog.dismiss()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                is State.Error -> {
                    progressDialog.dismiss()
                    toastError(
                        getString(com.github.ekamekas.baha.common.R.string.hint_create_operation_failed_format,
                            getString(R.string.transaction_record)
                        )
                    )
                }
                is State.Progress -> progressDialog.show()
            }.exhaustive
        })
        viewModel.onTransactionRecordDeleteEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Success -> {
                    progressDialog.dismiss()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                is State.Error -> {
                    progressDialog.dismiss()
                    toastError(
                        getString(com.github.ekamekas.baha.common.R.string.hint_delete_operation_failed_format,
                            getString(R.string.transaction_record)
                        )
                    )
                }
                is State.Progress -> progressDialog.show()
            }.exhaustive
        })
        viewModel.onNextPageClickEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Success -> {
                    // move next page item
                    dataBinding.vContent.currentItem = dataBinding.vContent.currentItem + 1
                }
            }
        })
    }

    override fun setupViews() {
        dataBinding.vNavigation.apply {
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_add -> {
                        viewModel.onTransactionRecordAdd()

                        true
                    }
                    R.id.action_delete -> {
                        onActionDelete()

                        true
                    }
                    else -> false
                }

            }
        }
        dataBinding.vContent.apply {
            adapter = TransactionRecordPageAdapter(this@TransactionRecordActivity)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    override fun didSetup() {
        intent.getParcelableExtra<TransactionRecordVO>(ExtraCode.TRANSACTION_RECORD)?.also {
            dataBinding.vNavigation.menu.findItem(R.id.action_add).title = getString(R.string.label_update_transaction)
            viewModel.setTransactionRecord(it.toDomain())
        }.also {
            dataBinding.vNavigation.menu.findItem(R.id.action_delete).isVisible = it != null
        }
    }

    override fun onBackPressed() {
        if(dataBinding.vContent.currentItem == 0) {
            // if user currently looking at the first page, then allow system to handle back button
            super.onBackPressed()
        } else {
            // otherwise, scroll to previous page
            dataBinding.vContent.currentItem = dataBinding.vContent.currentItem - 1
        }
    }

    // callback
    private fun onActionDelete() {
        if(!actionPromptDialog.isShowing) {
            actionPromptDialog.show()
        }
    }
}