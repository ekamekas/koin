package com.github.ekamekas.koin.transaction.domain.use_case.transaction_category

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ITransactionUseCase.TransactionCategory.GetTransactionCategoryUseCase]
 *
 * @param dispatcher implementation of [CoroutineDispatcher]
 * @param transactionCategoryRepository implementation of [ITransactionRepository.TransactionCategory]
 */
class GetTransactionCategoryUseCase @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val transactionCategoryRepository: ITransactionRepository.TransactionCategory
): ITransactionUseCase.TransactionCategory.GetTransactionCategoryUseCase() {

    override suspend fun run(params: Nothing?): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                transactionCategoryRepository.getTransactionCategory()
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun cancel() {
        throw NotImplementedError()
    }
}