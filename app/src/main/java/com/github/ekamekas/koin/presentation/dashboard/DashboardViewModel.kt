package com.github.ekamekas.koin.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ekamekas.baha.core.presentation.view_model.BaseViewModel
import com.github.ekamekas.baha.core.presentation.view_object.State
import javax.inject.Inject

class DashboardViewModel @Inject constructor(): BaseViewModel() {

    // data state

    // event
    private val _onAddTransactionEvent = MutableLiveData<State<Unit>>()
    val onAddTransactionEvent: LiveData<State<Unit>> = _onAddTransactionEvent

    // view bind
    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> = _balance
    private val _income = MutableLiveData<Double>()
    val income: LiveData<Double> = _income
    private val _outcome = MutableLiveData<Double>()
    val outcome: LiveData<Double> = _outcome
    private val _transactionRecord = MutableLiveData<List<Double>>()
    val transactionRecord: LiveData<List<Double>> = _transactionRecord

    init {
        getTransactionRecord()

        _transactionRecord.observeForever {  transactionRecord ->
            getBalance(transactionRecord)
            getIncome(transactionRecord)
            getOutcome(transactionRecord)
        }
    }

    /**
     * Called on add transaction event
     */
    fun onAddTransactionEvent() {
        _onAddTransactionEvent.value = State.Success(Unit)
    }

    private fun getBalance(transactionRecord: List<Double>) {
        _balance.value = if(transactionRecord.isEmpty()) 0.0 else transactionRecord.reduce { acc, value -> acc + value }
    }

    private fun getIncome(transactionRecord: List<Double>) {
        _income.value = if(transactionRecord.isEmpty()) 0.0 else transactionRecord.reduce { acc, value -> acc + value }
    }

    private fun getOutcome(transactionRecord: List<Double>) {
        _outcome.value = if(transactionRecord.isEmpty()) 0.0 else transactionRecord.reduce { acc, value -> acc + value }
    }

    private fun getTransactionRecord() {
        _transactionRecord.value = listOf()
    }

}