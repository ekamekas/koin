package com.github.ekamekas.koin.transaction.presentation.transaction_record

import android.app.Activity
import androidx.viewpager2.widget.ViewPager2
import com.github.ekamekas.baha.common.ext.buildDialogProgress
import com.github.ekamekas.baha.common.ext.exhaustive
import com.github.ekamekas.baha.common.ext.toastSuccess
import com.github.ekamekas.baha.core.presentation.activity.BaseActivityDataBinding
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.baha.core.presentation.view_object.StateObserver
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.ActivityTransactionRecordBinding
import com.github.ekamekas.koin.transaction.presentation.ExtraCode
import com.github.ekamekas.koin.transaction.presentation.view_object.TransactionRecordVO
import com.github.ekamekas.koin.transaction.presentation.view_object.toDomain
import kotlinx.android.synthetic.main.activity_transaction_record.*

/**
 * Activity to create, update, and view transaction record
 */
class TransactionRecordActivity: BaseActivityDataBinding<TransactionRecordViewModel, ActivityTransactionRecordBinding>() {

    // dialog
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
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                is State.Error -> {
                    progressDialog.dismiss()
                    toastSuccess(
                        getString(com.github.ekamekas.baha.common.R.string.hint_create_operation_failed_format,
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
                    vContent.currentItem = vContent.currentItem + 1
                }
            }
        })
    }

    override fun setupViews() {
        vNavigation.apply {
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_add -> {
                        viewModel.onTransactionRecordAdd()

                        true
                    }
                    else -> false
                }

            }
        }
        vContent.apply {
            adapter = TransactionRecordPageAdapter(this@TransactionRecordActivity)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    override fun didSetup() {
        intent.getParcelableExtra<TransactionRecordVO>(ExtraCode.TRANSACTION_RECORD)?.also {
            vNavigation.menu.findItem(R.id.action_add).title = getString(R.string.label_update_transaction)
            viewModel.setTransactionRecord(it.toDomain())
        }
    }

    override fun onBackPressed() {
        if(vContent.currentItem == 0) {
            // if user currently looking at the first page, then allow system to handle back button
            super.onBackPressed()
        } else {
            // otherwise, scroll to previous page
            vContent.currentItem = vContent.currentItem - 1
        }
    }
}