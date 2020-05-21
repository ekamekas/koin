package com.github.ekamekas.koin.transaction.presentation.transaction_record

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import com.github.ekamekas.baha.core.presentation.fragment.BaseFragmentDataBinding
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.databinding.FragmentTransactionRecordOptionalFormBinding
import kotlinx.android.synthetic.main.fragment_transaction_record_optional_form.*
import java.util.*

/**
 * Fragment to input transaction record optional data
 */
class TransactionRecordOptionalFormFragment: BaseFragmentDataBinding<TransactionRecordViewModel, FragmentTransactionRecordOptionalFormBinding>() {

    // dialog
    private val dateInputDialog by lazy {
        val currentDate = Calendar.getInstance().apply {
            timeInMillis = viewModel.transactionDateMillisBind.value ?: System.currentTimeMillis()
        }
        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                onDateSelected(year,month,dayOfMonth)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
    }
    private val timeInputDialog by lazy {
        val currentDate = Calendar.getInstance().apply {
            timeInMillis = viewModel.transactionDateMillisBind.value ?: System.currentTimeMillis()
        }
        TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                onTimeSelected(hourOfDay,minute)
            },
            currentDate.get(Calendar.HOUR_OF_DAY),
            currentDate.get(Calendar.MINUTE),
            true
        )
    }

    override val viewModelClazz: Class<TransactionRecordViewModel>
        get() = TransactionRecordViewModel::class.java
    override val resourceId: Int
        get() = R.layout.fragment_transaction_record_optional_form

    override fun setupBinding() {
        dataBinding.viewModel = viewModel
    }

    override fun setupObservers() {/*nop*/}

    override fun setupViews() {
        ivDate.setOnClickListener {
            delegateDateInput()
        }
        ivTime.setOnClickListener {
            delegateTimeInput()
        }
    }

    override fun didSetup() {/*nop*/}

    // callback
    private fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        val currentMillis = Calendar.getInstance().apply {
            viewModel.transactionDateMillisBind.value?.also { timeInMillis = it }
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }.timeInMillis
        viewModel.setTransactionDate(currentMillis)
    }
    private fun onTimeSelected(hour: Int, minute: Int) {
        val currentMillis = Calendar.getInstance().apply {
            viewModel.transactionDateMillisBind.value?.also { timeInMillis = it }
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }.timeInMillis
        viewModel.setTransactionDate(currentMillis)
    }

    // delegator
    private fun delegateDateInput() {
        if(!dateInputDialog.isShowing) {
            dateInputDialog.show()
        }
    }
    private fun delegateTimeInput() {
        if(!timeInputDialog.isShowing) {
            timeInputDialog.show()
        }
    }
}