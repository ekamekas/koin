package com.github.ekamekas.koin.transaction.data.source.transaction_category

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.data.source.TransactionDatabase
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Local implementation of [ITransactionDataSource.TransactionCategory]
 */
class TransactionCategoryLocalDataSource @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val database: TransactionDatabase
): ITransactionDataSource.TransactionCategory {

    override suspend fun deleteTransactionCategory(): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionCategoryDAO().delete() < 1) {
                    throw IllegalStateException("failed to delete")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteTransactionCategory(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionCategoryDAO().delete(id) < 1) {
                    throw IllegalStateException("failed to delete")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteTransactionCategoryByCategory(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionCategoryDAO().delete(id) < 1) {
                    throw IllegalStateException("failed to delete")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchTransactionCategory(): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                val result = database.transactionCategoryDAO().fetch()
                if(result.isEmpty()) {
                    throw IllegalStateException("data is empty")
                }
                Result.Success(result.map { it.toDomain() })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchTransactionCategory(id: String): Result<TransactionCategory> =
        withContext(dispatcher) {
            return@withContext try {
                val result = database.transactionCategoryDAO().fetch(id)
                Result.Success(result.toDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchTransactionCategoryByCategory(id: String): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                val result = database.transactionCategoryDAO().fetchByCategory(id)
                if(result.isEmpty()) {
                    throw IllegalStateException("data is empty")
                }
                Result.Success(result.map { it.toDomain() })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun upsertTransactionCategory(param: TransactionCategory): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionCategoryDAO().upsert(param.toDB()) < 1) {
                    throw IllegalStateException("failed to upsert")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun upsertTransactionCategory(param: List<TransactionCategory>): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionCategoryDAO().upsert(param.map { it.toDB() }) < 1) {
                    throw IllegalStateException("failed to upsert")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}