package com.github.ekamekas.koin.transaction.domain.use_case

import com.github.ekamekas.baha.core.domain.use_case.BaseUseCase

abstract class ITransactionUseCase {

    /**
     * Contract defined for transaction record use case
     */
    abstract class TransactionRecord {
        abstract class GetTransactionRecordUseCase: BaseUseCase<List<com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord>, Nothing?>()
        abstract class PutSingleTransactionRecordUseCase: BaseUseCase<Nothing?, PutSingleTransactionRecordUseCase.Param>() {
            data class Param(val transactionRecord: com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord)
        }
    }

}