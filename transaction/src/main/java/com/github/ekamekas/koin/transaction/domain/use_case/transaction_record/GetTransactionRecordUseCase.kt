package com.github.ekamekas.koin.transaction.domain.use_case.transaction_record

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ITransactionUseCase.TransactionRecord.GetTransactionRecordUseCase]
 *
 * @param dispatcher implementation of [CoroutineDispatcher]
 * @param transactionRecordRepository implementation of [ITransactionRepository.TransactionRecord]
 */
class GetTransactionRecordUseCase @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val transactionRecordRepository: ITransactionRepository.TransactionRecord
): ITransactionUseCase.TransactionRecord.GetTransactionRecordUseCase() {

    override suspend fun run(params: Nothing?): Result<List<TransactionRecord>> =
        withContext(dispatcher) {
            return@withContext transactionRecordRepository.getTransactionRecord()
        }

    override suspend fun cancel() {
        throw NotImplementedError()
    }
}