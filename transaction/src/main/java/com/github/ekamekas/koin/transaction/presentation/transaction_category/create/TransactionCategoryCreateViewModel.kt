package com.github.ekamekas.koin.transaction.presentation.transaction_category.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ekamekas.baha.core.presentation.view_model.BaseViewModel
import com.github.ekamekas.baha.core.presentation.view_object.State
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model of transaction category creation
 */
class TransactionCategoryCreateViewModel @Inject constructor(
    private val deleteTransactionCategoryUseCase: ITransactionUseCase.TransactionCategory.DeleteTransactionCategoryUseCase,
    private val putSingleTransactionCategoryUseCase: ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase
): BaseViewModel() {

    // data state
    private val _transactionCategoryState = MutableLiveData<TransactionCategory>()

    // event
    private val _onTransactionCategoryDeleteEvent = MutableLiveData<State<TransactionCategory>>()
    val onTransactionCategoryDeleteEvent: LiveData<State<TransactionCategory>> = _onTransactionCategoryDeleteEvent
    private val _onTransactionCategoryGetEvent = MutableLiveData<State<TransactionCategory>>()
    private val _onTransactionCategoryAddOrUpdateEvent = MutableLiveData<State<TransactionCategory>>()
    val onTransactionCategoryAddOrUpdateEvent: LiveData<State<TransactionCategory>> = _onTransactionCategoryAddOrUpdateEvent

    // view bind
    val transactionCategoryNameBind = MutableLiveData<String>()
    val transactionCategoryDescriptionBind = MutableLiveData<String>()

    init {
        _onTransactionCategoryGetEvent.observeForever { transactionCategoryEvent ->
            if(transactionCategoryEvent is State.Success) {
                _transactionCategoryState.value = transactionCategoryEvent.data
            }
        }

        _transactionCategoryState.observeForever { transactionCategory ->
            transactionCategory.name.also { transactionCategoryNameBind.value = it }
            transactionCategory.description?.also { transactionCategoryNameBind.value = it }
        }
    }

    /**
     * Transaction category setter
     */
    fun setTransactionCategory(transactionCategory: TransactionCategory) {
        _onTransactionCategoryGetEvent.value = State.Success(transactionCategory)
    }

    /**
     * Event on add or update transaction category
     */
    fun onTransactionCategoryAddOrUpdate() {
        _onTransactionCategoryAddOrUpdateEvent.value = State.Progress()
        viewModelScope.launch {
            val transactionCategory = _transactionCategoryState.value?.apply {
                name = transactionCategoryNameBind.value ?: ""
                description = transactionCategoryDescriptionBind.value
            } ?: TransactionCategory(
                name = transactionCategoryNameBind.value ?: "",
                description = transactionCategoryDescriptionBind.value,
                image = null
            )

            // invoke use case
            putSingleTransactionCategoryUseCase
                .invoke(
                    params = ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase.Param(transactionCategory)
                )
                .foldSuspend(
                    fnL = {
                        // on error
                        _onTransactionCategoryAddOrUpdateEvent.value = State.Error(exception = it)
                    },
                    fnR = {
                        // on success
                        _onTransactionCategoryAddOrUpdateEvent.value = State.Success(transactionCategory)
                    }
                )
        }
    }

    fun onTransactionCategoryDelete() {
        _onTransactionCategoryDeleteEvent.value = State.Progress()
        viewModelScope.launch {
            _transactionCategoryState.value.also { transactionCategory ->
                // validate transaction category is set
                if(transactionCategory != null) {
                    // invoke use case
                    deleteTransactionCategoryUseCase
                        .invoke(
                            params = ITransactionUseCase.TransactionCategory.DeleteTransactionCategoryUseCase.Param(transactionCategory.id)
                        )
                        .foldSuspend(
                            fnL = {
                                // on error
                                _onTransactionCategoryDeleteEvent.value = State.Error(exception = it)
                            },
                            fnR = {
                                // on success
                                _onTransactionCategoryDeleteEvent.value = State.Success(transactionCategory)
                            }
                        )
                } else {
                    _onTransactionCategoryDeleteEvent.value = State.Error(exception = IllegalStateException("transaction record is not set"))
                }
            }
        }
    }

}