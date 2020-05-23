package com.github.ekamekas.koin.presentation.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.Observer
import com.github.ekamekas.baha.common.ext.*
import com.github.ekamekas.baha.core.presentation.activity.BaseActivityDataBinding
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.baha.core.presentation.view_object.StateObserver
import com.github.ekamekas.koin.R
import com.github.ekamekas.koin.databinding.ActivityDashboardBinding
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.presentation.ExtraCode
import com.github.ekamekas.koin.transaction.presentation.RequestCode
import com.github.ekamekas.koin.transaction.presentation.adapter.TransactionRecordAdapter
import com.github.ekamekas.koin.transaction.presentation.transaction_record.TransactionRecordActivity
import com.github.ekamekas.koin.transaction.presentation.view_object.toVO
import kotlinx.android.synthetic.main.activity_dashboard.*

/**
 * Center activity for user interaction
 */
class DashboardActivity: BaseActivityDataBinding<DashboardViewModel, ActivityDashboardBinding>() {

    // adapter
    private val transactionRecordAdapter by lazy {
        TransactionRecordAdapter(
            onClick = ::onTransactionRecordClick
        )
    }

    private var didBackPress = false  // flag to indicate whether user did back press

    override val viewModelClazz: Class<DashboardViewModel>
        get() = DashboardViewModel::class.java
    override val resourceId: Int
        get() = R.layout.activity_dashboard

    override fun setupBinding() {
        dataBinding.viewModel = viewModel
    }

    override fun setupObservers() {
        // event
        viewModel.onAddTransactionRecordEvent.observe(this, StateObserver {
            delegateAddTransaction()
        })
        viewModel.onGetTransactionRecordEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Error -> toastError(getString(R.string.hint_get_operation_failed_format, getString(R.string.transaction_record)))
            }
        })

        // view
        viewModel.transactionRecord.observe(this, Observer { data ->
            vDataEmptyContainer.visibleIf { data.isEmpty() }
            transactionRecordAdapter.setData(data)
        })
    }

    override fun setupViews() {
        vTransactionRecordContent.initVerticalRecyclerView(
            context = this,
            adapter = transactionRecordAdapter,
            offset = dimens(R.dimen.margin_inter_item)
        )
    }

    override fun didSetup() {/*nop*/}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RequestCode.TRANSACTION_RECORD -> {
                if(Activity.RESULT_OK == resultCode) {
                    viewModel.getTransactionRecord()
                }
            }
        }
    }

    override fun onBackPressed() {
        if(didBackPress) {
            super.onBackPressed()
        } else {
            toastInfo(getString(R.string.hint_back_press_to_exit))
            didBackPress = true  // user did back press
            Handler().postDelayed({
                didBackPress = false  // reset flag state
            }, 1500L)  // delayed for 1.5 s
        }
    }

    // delegate
    private fun delegateAddTransaction() {
        Intent(this, TransactionRecordActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }.also {
            startActivityForResult(it, RequestCode.TRANSACTION_RECORD)
        }
    }
    private fun delegateUpdateTransaction(transactionRecord: TransactionRecord) {
        Intent(this, TransactionRecordActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ExtraCode.TRANSACTION_RECORD, transactionRecord.toVO())
        }.also {
            startActivityForResult(it, RequestCode.TRANSACTION_RECORD)
        }
    }

    // callback
    private fun onTransactionRecordClick(transactionRecord: TransactionRecord) {
        delegateUpdateTransaction(transactionRecord)
    }
}