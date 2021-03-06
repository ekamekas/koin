package com.github.ekamekas.koin.transaction.domain.use_case

import com.github.ekamekas.baha.core.domain.use_case.BaseUseCase

abstract class ITransactionUseCase {

    /**
     * Contract defined for transaction category use case
     */
    abstract class TransactionCategory {
        abstract class DeleteTransactionCategoryUseCase: BaseUseCase<Nothing?, DeleteTransactionCategoryUseCase.Param>() {
            data class Param(val id: String)
        }
        abstract class GetTransactionCategoryUseCase: BaseUseCase<List<com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory>, Nothing?>()
        abstract class PutSingleTransactionCategoryUseCase: BaseUseCase<Nothing?, PutSingleTransactionCategoryUseCase.Param>() {
            data class Param(val transactionCategory: com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory)
        }
    }

    /**
     * Contract defined for transaction record use case
     */
    abstract class TransactionRecord {
        abstract class DeleteTransactionRecordUseCase: BaseUseCase<Nothing?, DeleteTransactionRecordUseCase.Param>() {
            data class Param(val id: String)
        }
        abstract class GetTransactionRecordUseCase: BaseUseCase<List<com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord>, Nothing?>()
        abstract class PutSingleTransactionRecordUseCase: BaseUseCase<Nothing?, PutSingleTransactionRecordUseCase.Param>() {
            data class Param(val transactionRecord: com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord)
        }
    }

}