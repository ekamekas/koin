package com.github.ekamekas.koin.transaction.data.source.transaction_record

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.data.source.TransactionDatabase
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Local source implementation of [ITransactionDataSource.TransactionRecord]
 */
class TransactionRecordLocalDataSource @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val database: TransactionDatabase
): ITransactionDataSource.TransactionRecord {

    override suspend fun deleteTransactionRecord(): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionRecordDAO().delete() < 1) {
                    throw IllegalStateException("failed to delete")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteTransactionRecord(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionRecordDAO().delete(id) < 1) {
                    throw IllegalStateException("failed to delete")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchTransactionRecord(): Result<List<TransactionRecord>> =
        withContext(dispatcher) {
            return@withContext try {
                val result = database.transactionRecordDAO().fetch()
                Result.Success(result.map { it.toDomain() })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun fetchTransactionRecord(id: String): Result<TransactionRecord> =
        withContext(dispatcher) {
            return@withContext try {
                val result = database.transactionRecordDAO().fetch(id)
                Result.Success(result.toDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun upsertTransactionRecord(param: TransactionRecord): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionRecordDAO().upsert(param.toDB()) < 1) {
                    throw IllegalStateException("failed to upsert")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun upsertTransactionRecord(param: List<TransactionRecord>): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                if(database.transactionRecordDAO().upsert(param.map { it.toDB() }) < 1) {
                    throw IllegalStateException("failed to upsert")
                }
                Result.Success(null)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}