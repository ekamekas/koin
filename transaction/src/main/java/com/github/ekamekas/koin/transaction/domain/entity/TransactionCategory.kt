package com.github.ekamekas.koin.transaction.domain.entity

import java.util.*

/**
 * Entity of transaction category business model
 *
 * @param id identifier
 * @param name name of transaction category
 * @param description description of transaction category
 * @param image base64 representation of transaction category image
 * @param parentCategory parent object of this transaction category
 */
data class TransactionCategory(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String? = null,
    var image: String?,
    var parentCategory: TransactionCategory? = null
)
