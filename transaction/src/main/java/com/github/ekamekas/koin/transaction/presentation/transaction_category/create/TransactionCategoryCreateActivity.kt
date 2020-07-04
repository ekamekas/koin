package com.github.ekamekas.koin.transaction.presentation.transaction_category.create

import android.app.Activity
import android.content.Intent
import com.github.ekamekas.baha.common.ext.buildDialogActionPrompt
import com.github.ekamekas.baha.common.ext.buildDialogProgress
import com.github.ekamekas.baha.common.ext.exhaustive
import com.github.ekamekas.baha.common.ext.toastError
import com.github.ekamekas.baha.core.presentation.activity.BaseActivityDataBinding
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.baha.core.presentation.view_object.StateObserver
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.ActivityTransactionCategoryCreateBinding
import com.github.ekamekas.koin.transaction.presentation.ExtraCode
import com.github.ekamekas.koin.transaction.presentation.view_object.TransactionCategoryVO
import com.github.ekamekas.koin.transaction.presentation.view_object.toDomain
import com.github.ekamekas.koin.transaction.presentation.view_object.toVO

/**
 * Activity to create or update category
 */
class TransactionCategoryCreateActivity: BaseActivityDataBinding<TransactionCategoryCreateViewModel, ActivityTransactionCategoryCreateBinding>() {

    // dialog
    private val actionPromptDialog by lazy {
        buildDialogActionPrompt(
            title = getString(R.string.hint_delete_operation_prompt),
            onPositiveButtonClick = {
                viewModel.onTransactionCategoryDelete()
            },
            onNegativeButtonClick = {/*nop*/}
        )
    }
    private val progressDialog by lazy {
        buildDialogProgress()
    }

    override val viewModelClazz: Class<TransactionCategoryCreateViewModel>
        get() = TransactionCategoryCreateViewModel::class.java
    override val resourceId: Int
        get() = R.layout.activity_transaction_category_create

    override fun setupBinding() {
        dataBinding.viewModel = viewModel
    }

    override fun setupObservers() {
        viewModel.onTransactionCategoryAddOrUpdateEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Success -> {
                    progressDialog.dismiss()
                    val resultIntent = Intent().apply {
                        putExtra(ExtraCode.TRANSACTION_CATEGORY, state.data.toVO())
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
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
        viewModel.onTransactionCategoryDeleteEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Success -> {
                    progressDialog.dismiss()
                    val resultIntent = Intent().apply {
                        putExtra(ExtraCode.TRANSACTION_CATEGORY, state.data.toVO().apply { isDeleted = true })
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
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
    }

    override fun setupViews() {
        dataBinding.vNavigation.apply {
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_add -> {
                        viewModel.onTransactionCategoryAddOrUpdate()

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
    }

    override fun didSetup() {
        intent.getParcelableExtra<TransactionCategoryVO>(ExtraCode.TRANSACTION_CATEGORY)?.also {
            dataBinding.vNavigation.menu.findItem(R.id.action_add).title = getString(R.string.label_update_transaction)
            viewModel.setTransactionCategory(it.toDomain())
        }.also {
            dataBinding.vNavigation.menu.findItem(R.id.action_delete).isVisible = it != null
        }
    }

    // callback
    private fun onActionDelete() {
        if(!actionPromptDialog.isShowing) {
            actionPromptDialog.show()
        }
    }
}