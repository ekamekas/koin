package com.github.ekamekas.koin.transaction.domain.use_case.transaction_category

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteTransactionCategoryUseCase @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val transactionCategoryRepository: ITransactionRepository.TransactionCategory
): ITransactionUseCase.TransactionCategory.DeleteTransactionCategoryUseCase() {

    override suspend fun run(params: Param): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext transactionCategoryRepository.deleteTransactionCategory(params.id)
        }

    override suspend fun cancel() {
        throw NotImplementedError()
    }
}