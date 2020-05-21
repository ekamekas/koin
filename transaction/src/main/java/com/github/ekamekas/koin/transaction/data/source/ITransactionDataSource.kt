package com.github.ekamekas.koin.transaction.data.source

import com.github.ekamekas.baha.core.domain.entity.Result

interface ITransactionDataSource {

    /**
     * Contract defined for transaction record data source
     */
    interface TransactionRecord {
        suspend fun deleteTransactionRecord(): Result<Nothing?>
        suspend fun deleteTransactionRecord(id: String): Result<Nothing?>
        suspend fun fetchTransactionRecord(): Result<List<com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord>>
        suspend fun fetchTransactionRecord(id: String): Result<com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord>
        suspend fun upsertTransactionRecord(param: com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord): Result<Nothing?>
        suspend fun upsertTransactionRecord(param: List<com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord>): Result<Nothing?>
    }

}