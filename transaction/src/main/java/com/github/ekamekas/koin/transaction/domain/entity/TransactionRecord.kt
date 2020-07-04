package com.github.ekamekas.koin.transaction.domain.entity

import java.util.*

/**
 * Entity of transaction record business model
 *
 * @param id identifier
 * @param description description of transaction
 * @param transactionDate data of transaction in millis
 * @param transactionType type of transaction either [TransactionType.EXPENSE] or [TransactionType.INCOME]
 * @param transactionCategory category of transaction
 * @param value value of transaction
 *
 * @see TransactionCategory
 */
data class TransactionRecord(
    val id: String = UUID.randomUUID().toString(),
    var description: String? = null,
    var transactionDate: Long,
    var transactionType: String,
    var transactionCategory: TransactionCategory? = null,
    var value: Double
)

object TransactionType {
    const val EXPENSE = "EXPENSE"
    const val INCOME = "INCOME"
}