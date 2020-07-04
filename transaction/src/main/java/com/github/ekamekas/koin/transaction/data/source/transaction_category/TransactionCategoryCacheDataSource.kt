package com.github.ekamekas.koin.transaction.data.source.transaction_category

import com.github.ekamekas.baha.core.data.source.BaseCacheDataSource
import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ITransactionDataSource.TransactionCategory]
 */
class TransactionCategoryCacheDataSource @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher
): BaseCacheDataSource<TransactionCategory>(), ITransactionDataSource.TransactionCategory {

    override suspend fun deleteTransactionCategory(): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                deleteCache()
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun deleteTransactionCategory(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                fetchCache(id)
                    .mapRightSuspend { transactionCategory ->
                        if(transactionCategory.parentCategory == null) {
                            deleteTransactionCategoryByCategory(id)
                        } else {
                            deleteCache(id)
                        }
                    }
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun deleteTransactionCategoryByCategory(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                fetchTransactionCategoryByCategory(id)
                    .mapRightSuspend { transactionCategory ->
                        transactionCategory
                            .filter { it.id == id || it.parentCategory?.id == id }
                            .map { it.id }
                            .let {
                                deleteCache(it)
                            }
                    }
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun fetchTransactionCategory(): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                fetchCache()
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun fetchTransactionCategory(id: String): Result<TransactionCategory> =
        withContext(dispatcher) {
            return@withContext try {
                fetchCache(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun fetchTransactionCategoryByCategory(id: String): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                fetchCache()
                    .mapRightSuspend { transactionCategory ->
                        transactionCategory
                            .filter { it.id == id || it.parentCategory?.id == id }
                            .let {
                                if(it.isEmpty()) {
                                    throw IllegalStateException("Data is empty")
                                }
                                Result.Success(it)
                            }
                    }
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun upsertTransactionCategory(param: TransactionCategory): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(param.parentCategory != null) {
                    upsertTransactionCategory(listOf(param))
                } else {
                    upsertCache(param.id, param)
                }
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun upsertTransactionCategory(param: List<TransactionCategory>): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                upsertCache(param.mapNotNull { it.parentCategory }.plus(param).map { Pair(it.id, it) }.toMap())
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }
}