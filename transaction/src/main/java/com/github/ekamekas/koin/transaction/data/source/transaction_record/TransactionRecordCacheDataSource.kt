package com.github.ekamekas.koin.transaction.data.source.transaction_record

import com.github.ekamekas.baha.core.data.source.BaseCacheDataSource
import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Cache source implementation of [ITransactionDataSource.TransactionRecord]
 */
class TransactionRecordCacheDataSource @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher
): BaseCacheDataSource<TransactionRecord>(), ITransactionDataSource.TransactionRecord {

    override suspend fun deleteTransactionRecord(): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                deleteCache()
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun deleteTransactionRecord(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                deleteCache(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun fetchTransactionRecord(): Result<List<TransactionRecord>> =
        withContext(dispatcher) {
            return@withContext try {
                fetchCache()
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun fetchTransactionRecord(id: String): Result<TransactionRecord> =
        withContext(dispatcher) {
            return@withContext try {
                fetchCache(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun upsertTransactionRecord(param: TransactionRecord): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                upsertCache(param.id, param)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun upsertTransactionRecord(param: List<TransactionRecord>): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                upsertCache(param.map { Pair(it.id, it) }.toMap())
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }
}