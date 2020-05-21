package com.github.ekamekas.koin.transaction.domain.use_case.transaction_record

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import com.github.ekamekas.koin.transaction.domain.validator.ITransactionValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase]
 *
 * @param dispatcher implementation of [CoroutineDispatcher]
 * @param transactionRecordRepository implementation of [ITransactionRepository.TransactionRecord]
 * @param transactionRecordValidator implementation of [ITransactionValidator.TransactionRecord]
 */
class PutSingleTransactionRecordUseCase @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val transactionRecordRepository: ITransactionRepository.TransactionRecord,
    private val transactionRecordValidator: ITransactionValidator.TransactionRecord
): ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase() {

    override suspend fun run(params: Param): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext transactionRecordValidator
                .validate(params.transactionRecord)
                .mapRightSuspend {
                    transactionRecordRepository
                        .putTransactionRecord(params.transactionRecord)
                }
        }

    override suspend fun cancel() {
        throw NotImplementedError()
    }
}