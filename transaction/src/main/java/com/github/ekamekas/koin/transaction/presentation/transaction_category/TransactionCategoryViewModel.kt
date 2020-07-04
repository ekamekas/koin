package com.github.ekamekas.koin.transaction.presentation.transaction_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ekamekas.baha.core.presentation.view_model.BaseViewModel
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.baha.core.presentation.view_object.StateObserver
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model of transaction category
 */
class TransactionCategoryViewModel @Inject constructor(
    private val getTransactionCategoryUseCase: ITransactionUseCase.TransactionCategory.GetTransactionCategoryUseCase
): BaseViewModel() {

    // event
    private val _onTransactionCategoryGetEvent = MutableLiveData<State<List<TransactionCategory>>>()
    val onTransactionCategoryGetEvent: LiveData<State<List<TransactionCategory>>> = _onTransactionCategoryGetEvent
    private val _onTransactionCategorySelectEvent = MutableLiveData<State<TransactionCategory>>()
    val onTransactionCategorySelectEvent: LiveData<State<TransactionCategory>> = _onTransactionCategorySelectEvent

    // view bind
    private val _transactionCategory = MutableLiveData<List<TransactionCategory>>()
    val transactionCategory: LiveData<List<TransactionCategory>> = _transactionCategory

    init {
        _onTransactionCategoryGetEvent.observeForever(StateObserver { onTransactionCategoryGetEvent ->
            if(onTransactionCategoryGetEvent is State.Success) {
                _transactionCategory.value = onTransactionCategoryGetEvent.data
            }
        })

        getTransactionCategory()
    }

    /**
     * Get transaction category
     */
    private fun getTransactionCategory() {
        _onTransactionCategoryGetEvent.value = State.Progress()
        viewModelScope.launch {
            getTransactionCategoryUseCase
                .invoke(null)
                .foldSuspend(
                    fnL = {
                        _onTransactionCategoryGetEvent.value = State.Error(exception = it)
                    },
                    fnR = {
                        _onTransactionCategoryGetEvent.value = State.Success(data = it)
                    }
                )
        }
    }

    /**
     * Select or get transaction category
     * if param has children then get else select
     *
     * @param transactionCategory transaction category to filter
     *
     */
    fun getOrSelectTransactionCategory(transactionCategory: TransactionCategory? = null) {
        // set transaction category to bind from state
        (_onTransactionCategoryGetEvent.value as? State.Success)?.data
            ?.also { transactionCategoryState ->
                if(transactionCategoryState.any { it.parentCategory?.id == transactionCategory?.id }) {
                    _transactionCategory.value = transactionCategory
                        ?.let { transactionCategory ->
                            transactionCategoryState
                                .filter {
                                    it.id == transactionCategory.id ||
                                            it.parentCategory?.id == transactionCategory.id
                                }  // filter by id or one level of parent id
                        } ?: transactionCategoryState  // else set all
                } else {
                    transactionCategory?.also {
                        _onTransactionCategorySelectEvent.value = State.Success(it)
                    }
                }
            }
    }

    /**
     * Put transaction category to the list
     *
     * @param transactionCategory transaction category to filter
     *
     */
    fun putTransactionCategory(transactionCategory: TransactionCategory) {
        // set transaction category to bind from state
        (_onTransactionCategoryGetEvent.value as? State.Success)?.data
            ?.also { transactionCategoryState ->
                val newState = transactionCategoryState
                    .filterNot { it.id == transactionCategory.id }
                    .plus(transactionCategory)
                _onTransactionCategoryGetEvent.value = State.Success(newState)
            }
    }

    /**
     * Delete transaction category from the list
     *
     * @param transactionCategory transaction category to filter
     *
     */
    fun deleteTransactionCategory(transactionCategory: TransactionCategory) {
        // set transaction category to bind from state
        (_onTransactionCategoryGetEvent.value as? State.Success)?.data
            ?.also { transactionCategoryState ->
                val newState = transactionCategoryState
                    .filterNot { it.id == transactionCategory.id }
                _onTransactionCategoryGetEvent.value = State.Success(newState)
            }
    }

}