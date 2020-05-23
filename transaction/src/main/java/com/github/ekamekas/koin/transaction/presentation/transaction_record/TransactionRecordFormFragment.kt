package com.github.ekamekas.koin.transaction.presentation.transaction_record

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.github.ekamekas.baha.core.presentation.fragment.BaseFragmentDataBinding
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.baha.core.presentation.view_object.StateObserver
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.FragmentTransactionRecordFormBinding
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import com.github.ekamekas.koin.transaction.ext.toCurrency
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_transaction_record_form.*

/**
 * Fragment to input transaction record data
 */
class TransactionRecordFormFragment: BaseFragmentDataBinding<TransactionRecordViewModel, FragmentTransactionRecordFormBinding>() {

    override val viewModelClazz: Class<TransactionRecordViewModel>
        get() = TransactionRecordViewModel::class.java
    override val resourceId: Int
        get() = R.layout.fragment_transaction_record_form

    override fun setupBinding() {
        dataBinding.viewModel = viewModel
    }

    override fun setupObservers() {
        viewModel.onTransactionRecordSetEvent.observe(this, StateObserver { state ->
            when(state) {
                is State.Success -> vKeyboard.setText(state.data.value.toString())
            }
        })
        viewModel.transactionTypeBind.observe(this, Observer { type ->
            if(type == TransactionType.INCOME && vOptionContainer.selectedTabPosition != 0) {
                vOptionContainer.selectTab(vOptionContainer.getTabAt(0))
            } else if (type == TransactionType.EXPENSE && vOptionContainer.selectedTabPosition != 1) {
                vOptionContainer.selectTab(vOptionContainer.getTabAt(1))
            }
        })
    }

    override fun setupViews() {
        tvValue.doAfterTextChanged {
            vValueContent.fullScroll(View.FOCUS_RIGHT)  // focus on latest input
        }
        vKeyboard.setOnClickListener { text, operationResult ->
            tvValue.text = text.toCurrency()
            viewModel.setTransactionValue(operationResult)
        }
        vOptionContainer.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {/*nop*/}

            override fun onTabUnselected(tab: TabLayout.Tab?) {/*no*/}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                onTabSelectedChange(tab)
            }
        })
    }

    override fun didSetup() {/*nop*/}

    // callback
    private fun onTabSelectedChange(tab: TabLayout.Tab?) {
        when(tab?.position) {
            0 -> viewModel.setTransactionType(TransactionType.INCOME)
            1 -> viewModel.setTransactionType(TransactionType.EXPENSE)
        }
    }
}