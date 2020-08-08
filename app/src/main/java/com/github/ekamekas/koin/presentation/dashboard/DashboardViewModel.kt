package com.github.ekamekas.koin.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ekamekas.baha.core.presentation.view_model.BaseViewModel
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getTransactionRecordUseCase: ITransactionUseCase.TransactionRecord.GetTransactionRecordUseCase
): BaseViewModel() {

    // data state

    // event
    private val _onAddTransactionRecordEvent = MutableLiveData<State<Unit>>()
    val onAddTransactionRecordEvent: LiveData<State<Unit>> = _onAddTransactionRecordEvent
    private val _onGetTransactionRecordEvent = MutableLiveData<State<List<TransactionRecord>>>()
    val onGetTransactionRecordEvent: LiveData<State<List<TransactionRecord>>> = _onGetTransactionRecordEvent

    // view bind
    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> = _balance
    private val _income = MutableLiveData<Double>()
    val income: LiveData<Double> = _income
    private val _expense = MutableLiveData<Double>()
    val expense: LiveData<Double> = _expense
    private val _transactionRecord = MutableLiveData<List<TransactionRecord>>()
    val transactionRecord: LiveData<List<TransactionRecord>> = _transactionRecord

    init {
        // event
        _onGetTransactionRecordEvent.observeForever { state ->
            if(state is State.Success) {
                _transactionRecord.value = state.data.sortedBy { it.transactionDate }
            }
        }

        // view bind
        _transactionRecord.observeForever {  transactionRecord ->
            getBalance(transactionRecord)
            getIncome(transactionRecord)
            getOutcome(transactionRecord)
        }

        getTransactionRecord()
    }

    /**
     * Get transaction record
     */
    fun getTransactionRecord() {
        _onGetTransactionRecordEvent.value = State.Progress()
        viewModelScope.launch {
            getTransactionRecordUseCase
                .invoke(null)
                .foldSuspend(
                    fnL = {
                        _onGetTransactionRecordEvent.value = State.Error(exception = it)
                    },
                    fnR = {
                        _onGetTransactionRecordEvent.value = State.Success(data = it)
                    }
                )
        }
    }

    /**
     * Called on add transaction event
     */
    fun onAddTransactionEvent() {
        _onAddTransactionRecordEvent.value = State.Success(Unit)
    }

    private fun getBalance(transactionRecord: List<TransactionRecord>) {
        _balance.value = if(transactionRecord.isEmpty()) 0.0 else transactionRecord.map { if(it.transactionType == TransactionType.INCOME) it.value else it.value.times(-1) }.reduce { acc, value -> acc + value }
    }

    private fun getIncome(transactionRecord: List<TransactionRecord>) {
        _income.value = transactionRecord.filter { it.transactionType == TransactionType.INCOME }.let { _transactionRecord ->
            if(_transactionRecord.isEmpty()) 0.0 else _transactionRecord.map { it.value }.reduce { acc, value -> acc + value }
        }
    }

    private fun getOutcome(transactionRecord: List<TransactionRecord>) {
        _expense.value = transactionRecord.filter { it.transactionType == TransactionType.EXPENSE }.let { _transactionRecord ->
            if(_transactionRecord.isEmpty()) 0.0 else _transactionRecord.map { it.value }.reduce { acc, value -> acc + value }
        }
    }

}