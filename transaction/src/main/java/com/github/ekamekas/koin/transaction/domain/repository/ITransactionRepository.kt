package com.github.ekamekas.koin.transaction.domain.repository

import com.github.ekamekas.baha.core.domain.entity.Result

interface ITransactionRepository {

    /**
     * Contract defined for transaction category repository
     */
    interface TransactionCategory {
        suspend fun deleteTransactionCategory(): Result<Nothing?>
        suspend fun deleteTransactionCategory(id: String): Result<Nothing?>
        suspend fun deleteTransactionCategoryByCategory(id: String): Result<Nothing?>
        suspend fun getTransactionCategory(): Result<List<com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory>>
        suspend fun getTransactionCategory(id: String): Result<com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory>
        suspend fun getTransactionCategoryByCategory(id: String): Result<List<com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory>>
        suspend fun putTransactionCategory(param: com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory): Result<Nothing?>
        suspend fun putTransactionCategory(param: List<com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory>): Result<Nothing?>
    }

    /**
     * Contract defined for transaction record repository
     */
    interface TransactionRecord {
        suspend fun deleteTransactionRecord(): Result<Nothing?>
        suspend fun deleteTransactionRecord(id: String): Result<Nothing?>
        suspend fun getTransactionRecord(): Result<List<com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord>>
        suspend fun getTransactionRecord(id: String): Result<com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord>
        suspend fun putTransactionRecord(param: com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord): Result<Nothing?>
        suspend fun putTransactionRecord(param: List<com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord>): Result<Nothing?>
    }

}