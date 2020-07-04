package com.github.ekamekas.koin.transaction.data.source.transaction_category

/**
 * Contract defined for transaction category service
 */
interface TransactionCategoryService {

    suspend fun fetch(): List<TransactionCategoryDTO>
    suspend fun fetch(id: String): TransactionCategoryDTO
    suspend fun fetchByCategory(id: String): List<TransactionCategoryDTO>

}