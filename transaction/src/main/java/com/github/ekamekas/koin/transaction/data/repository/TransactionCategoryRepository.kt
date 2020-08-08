package com.github.ekamekas.koin.transaction.data.repository

import com.github.ekamekas.baha.core.di.CacheDataSource
import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.di.LocalDataSource
import com.github.ekamekas.baha.core.di.RemoteDataSource
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ITransactionRepository.TransactionCategory]
 */
class TransactionCategoryRepository @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    @CacheDataSource private val transactionCategoryCacheDataSource: ITransactionDataSource.TransactionCategory,
    @LocalDataSource private val transactionCategoryLocalDataSource: ITransactionDataSource.TransactionCategory,
    @RemoteDataSource private val transactionCategoryRemoteDataSource: ITransactionDataSource.TransactionCategory
): ITransactionRepository.TransactionCategory{

    override suspend fun deleteTransactionCategory(): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                deleteFromDataSource()
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun deleteTransactionCategory(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                deleteFromDataSource(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun deleteTransactionCategoryByCategory(id: String): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                deleteByCategoryFromDataSource(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun getTransactionCategory(): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                getFromDataSource()
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun getTransactionCategory(id: String): Result<TransactionCategory> =
        withContext(dispatcher) {
            return@withContext try {
                getFromDataSource(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun getTransactionCategoryByCategory(id: String): Result<List<TransactionCategory>> =
        withContext(dispatcher) {
            return@withContext try {
                getByCategoryFromDataSource(id)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun putTransactionCategory(param: TransactionCategory): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                putIntoDataSource(param)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    override suspend fun putTransactionCategory(param: List<TransactionCategory>): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext try {
                putIntoDataSource(param)
            } catch (o_O: Exception) {
                Result.Error(o_O)
            }
        }

    private suspend fun deleteFromDataSource(): Result<Nothing?> {
        return transactionCategoryLocalDataSource
            .deleteTransactionCategory()
            .foldRightSuspend {
                transactionCategoryCacheDataSource
                    .deleteTransactionCategory()
            }
    }

    private suspend fun deleteFromDataSource(id: String): Result<Nothing?> {
        return transactionCategoryLocalDataSource
            .deleteTransactionCategory(id)
            .foldRightSuspend {
                transactionCategoryCacheDataSource
                    .deleteTransactionCategory(id)
            }
    }

    private suspend fun deleteByCategoryFromDataSource(id: String): Result<Nothing?> {
        return transactionCategoryLocalDataSource
            .deleteTransactionCategoryByCategory(id)
            .foldRightSuspend {
                transactionCategoryCacheDataSource
                    .deleteTransactionCategoryByCategory(id)
            }
    }

    private suspend fun getFromDataSource(): Result<List<TransactionCategory>> {
        return transactionCategoryCacheDataSource
            .fetchTransactionCategory()
            .mapSuspend(
                fnL = {
                    transactionCategoryLocalDataSource
                        .fetchTransactionCategory()
                        .foldRightSuspend { result ->
                            transactionCategoryCacheDataSource
                                .upsertTransactionCategory(result)
                        }
                },
                fnR = { result ->
                    Result.Success(result)
                })
            .mapSuspend(
                fnL = {
                    transactionCategoryRemoteDataSource
                        .fetchTransactionCategory()
                        .foldRightSuspend { result ->
                            transactionCategoryLocalDataSource
                                .upsertTransactionCategory(result)
                                .foldRightSuspend {
                                    transactionCategoryCacheDataSource
                                        .upsertTransactionCategory(result)
                                }
                        }
                },
                fnR = { result ->
                    Result.Success(result)
                }
            )
    }

    private suspend fun getFromDataSource(id: String): Result<TransactionCategory> {
        return transactionCategoryCacheDataSource
            .fetchTransactionCategory(id)
            .mapSuspend(
                fnL = {
                    transactionCategoryLocalDataSource
                        .fetchTransactionCategory(id)
                        .foldRightSuspend { result ->
                            transactionCategoryCacheDataSource
                                .upsertTransactionCategory(result)
                        }
                },
                fnR = { result ->
                    Result.Success(result)
                })
            .mapSuspend(
                fnL = {
                    transactionCategoryRemoteDataSource
                        .fetchTransactionCategory(id)
                        .foldRightSuspend { result ->
                            transactionCategoryLocalDataSource
                                .upsertTransactionCategory(result)
                                .foldRightSuspend {
                                    transactionCategoryCacheDataSource
                                        .upsertTransactionCategory(result)
                                }
                        }
                },
                fnR = { result ->
                    Result.Success(result)
                }
            )
    }

    private suspend fun getByCategoryFromDataSource(id: String): Result<List<TransactionCategory>> {
        return transactionCategoryCacheDataSource
            .fetchTransactionCategoryByCategory(id)
            .mapSuspend(
                fnL = {
                    transactionCategoryLocalDataSource
                        .fetchTransactionCategoryByCategory(id)
                        .foldRightSuspend { result ->
                            transactionCategoryCacheDataSource
                                .upsertTransactionCategory(result)
                        }
                },
                fnR = { result ->
                    Result.Success(result)
                })
            .mapSuspend(
                fnL = {
                    transactionCategoryRemoteDataSource
                        .fetchTransactionCategoryByCategory(id)
                        .foldRightSuspend { result ->
                            transactionCategoryLocalDataSource
                                .upsertTransactionCategory(result)
                                .foldRightSuspend {
                                    transactionCategoryCacheDataSource
                                        .upsertTransactionCategory(result)
                                }
                        }
                },
                fnR = { result ->
                    Result.Success(result)
                }
            )
    }

    private suspend fun putIntoDataSource(param: TransactionCategory): Result<Nothing?> {
        return transactionCategoryLocalDataSource
            .upsertTransactionCategory(param)
            .foldRightSuspend {
                transactionCategoryCacheDataSource
                    .upsertTransactionCategory(param)
            }
    }

    private suspend fun putIntoDataSource(param: List<TransactionCategory>): Result<Nothing?> {
        return transactionCategoryLocalDataSource
            .upsertTransactionCategory(param)
            .foldRightSuspend {
                transactionCategoryCacheDataSource
                    .upsertTransactionCategory(param)
            }
    }

}