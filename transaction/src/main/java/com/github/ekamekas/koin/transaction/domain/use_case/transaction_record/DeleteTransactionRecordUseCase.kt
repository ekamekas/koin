package com.github.ekamekas.koin.transaction.domain.use_case.transaction_record

import com.github.ekamekas.baha.core.di.DispatcherIO
import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteTransactionRecordUseCase @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val transactionRecordRepository: ITransactionRepository.TransactionRecord
): ITransactionUseCase.TransactionRecord.DeleteTransactionRecordUseCase() {

    override suspend fun run(params: Param): Result<Nothing?> =
        withContext(dispatcher) {
            return@withContext transactionRecordRepository.deleteTransactionRecord(params.id)
        }

    override suspend fun cancel() {
        throw NotImplementedError()
    }
}