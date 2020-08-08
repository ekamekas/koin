package com.github.ekamekas.koin.transaction.data.source.transaction_category

import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import kotlinx.serialization.Serializable
import java.util.*

/**
 * Data transfer object representation of TransactionCategory
 *
 * @param id identifier
 * @param name name of transaction category
 * @param description description of transaction category
 * @param image base64 representation of transaction category image
 * @param parentCategory parent object of this transaction category
 */
@Serializable
data class TransactionCategoryDTO(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String? = null,
    var image: String?,
    var parentCategory: TransactionCategoryDTO? = null
)

/**
 * Map DTO to domain object
 */
fun TransactionCategoryDTO.toDomain(): TransactionCategory {
    return TransactionCategory(
        id = id,
        name = name,
        description = description,
        image = image,
        parentCategory = parentCategory?.toDomain()
    )
}