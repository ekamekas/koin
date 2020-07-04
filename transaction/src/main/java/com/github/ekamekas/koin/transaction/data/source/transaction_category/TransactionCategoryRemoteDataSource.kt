package com.github.ekamekas.koin.transaction.data.source.transaction_category

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Remote implementation of [ITransactionDataSource.TransactionCategory]
 */
class TransactionCategoryRemoteDataSource @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val transactionCategoryService: TransactionCategoryService
): ITransactionDataSource.TransactionCategory {

    override suspend fun deleteTransactionCategory(): Result<Nothing?> {
        throw NotImplementedError()
    }

    override suspend fun deleteTransactionCategory(id: String): Result<Nothing?> {
        throw NotImplementedError()
    }

    override suspend fun deleteTransactionCategoryByCategory(id: String): Result<Nothing?> {
        throw NotImplementedError()
    }

    override suspend fun fetchTransactionCategory(): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                val result = transactionCategoryService.fetch()
                Result.Success(result.map { it.toDomain() })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchTransactionCategory(id: String): Result<TransactionCategory> =
        withContext(dispatcher) {
            return@withContext try {
                val result = transactionCategoryService.fetch(id)
                Result.Success(result.toDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchTransactionCategoryByCategory(id: String): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                val result = transactionCategoryService.fetchByCategory(id)
                Result.Success(result.map { it.toDomain() })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun upsertTransactionCategory(param: TransactionCategory): Result<Nothing?> {
        throw NotImplementedError()
    }

    override suspend fun upsertTransactionCategory(param: List<TransactionCategory>): Result<Nothing?> {
        throw NotImplementedError()
    }
}