package com.github.ekamekas.koin.transaction.data.repository

import com.github.ekamekas.baha.core.di.CacheDataSource
import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.di.LocalDataSource
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ITransactionRepository.TransactionRecord]
 */
class TransactionRecordRepository @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    @CacheDataSource private val transactionRecordCacheDataSource: ITransactionDataSource.TransactionRecord,
    @LocalDataSource private val transactionRecordLocalDataSource: ITransactionDataSource.TransactionRecord
): ITransactionRepository.TransactionRecord{

    override suspend fun deleteTransactionRecord(): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                deleteFromDataSource()
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun deleteTransactionRecord(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                deleteFromDataSource(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun getTransactionRecord(): Result<List<TransactionRecord>> =
        withContext(dispatcher) {
            return@withContext try {
                getFromDataSource()
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun getTransactionRecord(id: String): Result<TransactionRecord> =
        withContext(dispatcher) {
            return@withContext try {
                getFromDataSource(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun putTransactionRecord(param: TransactionRecord): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                putIntoDataSource(param)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun putTransactionRecord(param: List<TransactionRecord>): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                putIntoDataSource(param)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    private suspend fun deleteFromDataSource(): Result<Nothing?> {
        return transactionRecordLocalDataSource
            .deleteTransactionRecord()
            .foldRightSuspend {
                transactionRecordCacheDataSource
                    .deleteTransactionRecord()
            }
    }

    private suspend fun deleteFromDataSource(id: String): Result<Nothing?> {
        return transactionRecordLocalDataSource
            .deleteTransactionRecord(id)
            .foldRightSuspend {
                transactionRecordCacheDataSource
                    .deleteTransactionRecord(id)
            }
    }

    private suspend fun getFromDataSource(): Result<List<TransactionRecord>> {
        return transactionRecordCacheDataSource
            .fetchTransactionRecord()
            .mapSuspend(
                fnL = {
                    transactionRecordLocalDataSource
                        .fetchTransactionRecord()
                        .foldRightSuspend { localResult ->
                            transactionRecordCacheDataSource
                                .upsertTransactionRecord(localResult)
                        }
                },
                fnR = { cacheResult ->
                    Result.Success(cacheResult)
                }
            )
    }

    private suspend fun getFromDataSource(id: String): Result<TransactionRecord> {
        return transactionRecordCacheDataSource
            .fetchTransactionRecord(id)
            .mapSuspend(
                fnL = {
                    transactionRecordLocalDataSource
                        .fetchTransactionRecord(id)
                        .foldRightSuspend { localResult ->
                            transactionRecordCacheDataSource
                                .upsertTransactionRecord(localResult)
                        }
                },
                fnR = { cacheResult ->
                    Result.Success(cacheResult)
                }
            )
    }

    private suspend fun putIntoDataSource(param: TransactionRecord): Result<Nothing?> {
        return transactionRecordLocalDataSource
            .upsertTransactionRecord(param)
            .foldRightSuspend {
                transactionRecordCacheDataSource
                    .upsertTransactionRecord(param)
            }
    }

    private suspend fun putIntoDataSource(param: List<TransactionRecord>): Result<Nothing?> {
        return transactionRecordLocalDataSource
            .upsertTransactionRecord(param)
            .foldRightSuspend {
                transactionRecordCacheDataSource
                    .upsertTransactionRecord(param)
            }
    }
}