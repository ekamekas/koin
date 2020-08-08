package com.github.ekamekas.koin.transaction.presentation.transaction_record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ekamekas.baha.common.ext.toDate
import com.github.ekamekas.baha.common.ext.toTime
import com.github.ekamekas.baha.core.presentation.view_model.BaseViewModel
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model of transaction
 */
class TransactionRecordViewModel @Inject constructor(
    private val deleteTransactionRecordUseCase: ITransactionUseCase.TransactionRecord.DeleteTransactionRecordUseCase,
    private val putSingleTransactionRecordUseCase: ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase
): BaseViewModel() {

    // data state
    private val transactionRecordState = MutableLiveData<TransactionRecord>()

    // event
    private val _onTransactionRecordAddEvent = MutableLiveData<State<Unit>>()
    val onTransactionRecordAddEvent: LiveData<State<Unit>> = _onTransactionRecordAddEvent
    private val _onTransactionRecordDeleteEvent = MutableLiveData<State<Unit>>()
    val onTransactionRecordDeleteEvent: LiveData<State<Unit>> = _onTransactionRecordDeleteEvent
    private val _onTransactionRecordSetEvent = MutableLiveData<State<TransactionRecord>>()
    val onTransactionRecordSetEvent: LiveData<State<TransactionRecord>> = _onTransactionRecordSetEvent
    private val _onNextPageClickEvent = MutableLiveData<State<Unit>>()
    val onNextPageClickEvent: LiveData<State<Unit>> = _onNextPageClickEvent

    // view bind
    val descriptionBind = MutableLiveData<String>()
    private val _transactionDateMillisBind = MutableLiveData(System.currentTimeMillis())
    val transactionDateMillisBind: LiveData<Long> = _transactionDateMillisBind
    private val _transactionDateBind = MutableLiveData<String>()
    val transactionDateBind: LiveData<String> = _transactionDateBind
    private val _transactionTimeBind = MutableLiveData<String>()
    val transactionTimeBind: LiveData<String> = _transactionTimeBind
    private val _transactionTypeBind = MutableLiveData(TransactionType.INCOME)
    val transactionTypeBind: LiveData<String> = _transactionTypeBind
    private val _valueBind = MutableLiveData<Double>()
    private val _transactionCategoryBind = MutableLiveData<TransactionCategory>()
    val transactionCategory: LiveData<TransactionCategory> = _transactionCategoryBind

    init {
        _onTransactionRecordSetEvent.observeForever { state ->
            if(state is State.Success) {
                transactionRecordState.value = state.data
            }
        }
        transactionRecordState.observeForever { transactionRecord ->
            transactionRecord.description?.also { descriptionBind.value = it }
            transactionRecord.transactionDate.also { _transactionDateMillisBind.value = it }
            transactionRecord.transactionType.also { _transactionTypeBind.value = it }
            transactionRecord.transactionCategory.also { _transactionCategoryBind.value = it }
            transactionRecord.value.also { _valueBind.value = it }
        }
        _transactionDateMillisBind.observeForever { millis ->
            _transactionDateBind.value = millis.toDate()
            _transactionTimeBind.value = millis.toTime()
        }
    }

    /**
     * Transaction date setter
     */
    fun setTransactionDate(millis: Long) {
        _transactionDateMillisBind.value = millis
    }

    /**
     * Transaction date setter
     */
    fun setTransactionRecord(data: TransactionRecord) {
        _onTransactionRecordSetEvent.value = State.Success(data)
    }

    /**
     * Transaction type setter
     *
     * @see TransactionType
     */
    fun setTransactionType(type: String) {
        _transactionTypeBind.value = type
    }

    /**
     * Transaction value setter
     */
    fun setTransactionValue(value: Double) {
        _valueBind.value = value
    }

    /**
     * Transaction category setter
     */
    fun setTransactionCategory(value: TransactionCategory?) {
        _transactionCategoryBind.value = value
    }

    /**
     * Event on add transaction record
     */
    fun onTransactionRecordAdd() {
        _onTransactionRecordAddEvent.value = State.Progress()
        viewModelScope.launch {
            val transactionRecord = transactionRecordState.value?.apply {
                description = descriptionBind.value
                transactionDate = _transactionDateMillisBind.value ?: System.currentTimeMillis()
                transactionType = _transactionTypeBind.value ?: TransactionType.INCOME
                transactionCategory = _transactionCategoryBind.value
                value = _valueBind.value ?: 0.0
            } ?: TransactionRecord(
                description = descriptionBind.value,
                transactionDate = _transactionDateMillisBind.value ?: System.currentTimeMillis(),
                transactionType = _transactionTypeBind.value ?: TransactionType.INCOME,
                transactionCategory = _transactionCategoryBind.value,
                value = _valueBind.value ?: 0.0
            )

            // invoke use case
            putSingleTransactionRecordUseCase
                .invoke(
                    params = ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase.Param(transactionRecord)
                )
                .foldSuspend(
                    fnL = {
                        // on error
                        _onTransactionRecordAddEvent.value = State.Error(exception = it)
                    },
                    fnR = {
                        // on success
                        _onTransactionRecordAddEvent.value = State.Success(Unit)
                    }
                )
        }
    }

    /**
     * Event on delete transaction record
     */
    fun onTransactionRecordDelete() {
        _onTransactionRecordDeleteEvent.value = State.Progress()
        viewModelScope.launch {
            transactionRecordState.value.also { _transactionRecord ->
                // validate transaction record is set
                if(_transactionRecord != null) {
                    deleteTransactionRecordUseCase
                        .invoke(params = ITransactionUseCase.TransactionRecord.DeleteTransactionRecordUseCase.Param(_transactionRecord.id))
                        .foldSuspend(
                            fnL = {
                                _onTransactionRecordDeleteEvent.value = State.Error(exception = it)
                            },
                            fnR = {
                                _onTransactionRecordDeleteEvent.value = State.Success(Unit)
                            }
                        )
                } else {
                    _onTransactionRecordDeleteEvent.value = State.Error(exception = IllegalStateException("transaction record is not set"))
                }
            }
        }
    }

    /**
     * Event on next page click
     */
    fun onNextPageClickEvent() {
        _onNextPageClickEvent.value = State.Success(Unit)
    }

}