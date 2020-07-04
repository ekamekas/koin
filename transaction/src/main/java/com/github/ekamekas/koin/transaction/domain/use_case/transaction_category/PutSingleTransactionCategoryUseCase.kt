package com.github.ekamekas.koin.transaction.domain.use_case.transaction_category

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import com.github.ekamekas.koin.transaction.domain.validator.ITransactionValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase]
 *
 * @param dispatcher implementation of [CoroutineDispatcher]
 * @param transactionCategoryRepository implementation of [ITransactionRepository.TransactionCategory]
 */
class PutSingleTransactionCategoryUseCase @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val transactionCategoryRepository: ITransactionRepository.TransactionCategory,
    private val transactionCategoryValidator: ITransactionValidator.TransactionCategory
): ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase() {

    override suspend fun run(params: Param): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                transactionCategoryValidator
                    .validate(params.transactionCategory)
                    .mapRightSuspend {
                        transactionCategoryRepository
                            .putTransactionCategory(params.transactionCategory)
                    }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun cancel() {
        throw NotImplementedError()
    }
}