package com.github.ekamekas.koin.transaction.presentation.transaction_record

import android.app.Activity
import android.content.Intent
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
import com.github.ekamekas.koin.transaction.presentation.ExtraCode
import com.github.ekamekas.koin.transaction.presentation.RequestCode
import com.github.ekamekas.koin.transaction.presentation.transaction_category.TransactionCategoryActivity
import com.github.ekamekas.koin.transaction.presentation.view_object.TransactionCategoryVO
import com.github.ekamekas.koin.transaction.presentation.view_object.toDomain
import com.google.android.material.tabs.TabLayout

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
                is State.Success -> dataBinding.vKeyboard.setText(state.data.value.toString())
            }
        })
        viewModel.transactionTypeBind.observe(this, Observer { type ->
            if(type == TransactionType.INCOME && dataBinding.vOptionContainer.selectedTabPosition != 0) {
                dataBinding.vOptionContainer.selectTab(dataBinding.vOptionContainer.getTabAt(0))
            } else if (type == TransactionType.EXPENSE && dataBinding.vOptionContainer.selectedTabPosition != 1) {
                dataBinding.vOptionContainer.selectTab(dataBinding.vOptionContainer.getTabAt(1))
            }
        })
    }

    override fun setupViews() {
        dataBinding.tvValue.doAfterTextChanged {
            dataBinding. vValueContent.fullScroll(View.FOCUS_RIGHT)  // focus on latest input
        }
        dataBinding.vKeyboard.setOnClickListener { text, operationResult ->
            dataBinding.tvValue.text = text.toCurrency()
            viewModel.setTransactionValue(operationResult)
        }
        dataBinding.vOptionContainer.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {/*nop*/}

            override fun onTabUnselected(tab: TabLayout.Tab?) {/*no*/}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                onTabSelectedChange(tab)
            }
        })
        dataBinding.vCategory.setOnClickListener {
            delegateTransactionCategory()
        }
    }

    override fun didSetup() {/*nop*/}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RequestCode.TRANSACTION_CATEGORY -> {
                if(resultCode == Activity.RESULT_OK) {
                    data?.getParcelableExtra<TransactionCategoryVO>(ExtraCode.TRANSACTION_CATEGORY)
                        ?.also { transactionCategory ->
                            viewModel.setTransactionCategory(transactionCategory.toDomain())
                        }
                }
            }
        }
    }

    // callback
    private fun onTabSelectedChange(tab: TabLayout.Tab?) {
        when(tab?.position) {
            0 -> viewModel.setTransactionType(TransactionType.INCOME)
            1 -> viewModel.setTransactionType(TransactionType.EXPENSE)
        }
    }

    // delegator
    private fun delegateTransactionCategory() {
        val requestIntent = Intent(requireContext(), TransactionCategoryActivity::class.java)
        requestIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivityForResult(requestIntent, RequestCode.TRANSACTION_CATEGORY)
    }
}