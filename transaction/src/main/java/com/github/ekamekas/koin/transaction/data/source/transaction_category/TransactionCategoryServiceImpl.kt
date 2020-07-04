package com.github.ekamekas.koin.transaction.data.source.transaction_category

import com.github.ekamekas.baha.core.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [TransactionCategoryService]
 */
class TransactionCategoryServiceImpl @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher
): TransactionCategoryService {

    private var source: List<TransactionCategoryDTO> = listOf(
        TransactionCategoryDTO(
            name = "Housing",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Transportation",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Food",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Utilities",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Insurance",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Medical & Healthcare",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Saving & Investment",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Lifestyle",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Entertainment",
            image = null
        ),
        TransactionCategoryDTO(
            name = "Miscellaneous",
            image = null
        )
    )

    @Throws(Exception::class)
    override suspend fun fetch(): List<TransactionCategoryDTO> =
        withContext(dispatcher) {
            return@withContext source
        }

    @Throws(Exception::class)
    override suspend fun fetch(id: String): TransactionCategoryDTO =
        withContext(dispatcher) {
            return@withContext source
                .firstOrNull { it.id == id }
                ?: throw IllegalStateException("data not found")
        }

    @Throws(Exception::class)
    override suspend fun fetchByCategory(id: String): List<TransactionCategoryDTO> =
        withContext(dispatcher) {
            return@withContext source
                .filter { it.id == id || it.parentCategory?.id == id }
                .ifEmpty {
                    throw IllegalStateException("data not found")
                }
        }
}