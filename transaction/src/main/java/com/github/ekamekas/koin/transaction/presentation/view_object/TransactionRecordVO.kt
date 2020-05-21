package com.github.ekamekas.koin.transaction.presentation.view_object

import android.os.Parcelable
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Database representation of TransactionRecord
 *
 * @param id identifier
 * @param description description of transaction
 * @param transactionDate data of transaction in millis
 * @param transactionType type of transaction either [TransactionType.EXPENSE] or [TransactionType.INCOME]
 * @param value value of transaction
 */
@Parcelize
data class TransactionRecordVO(
    val id: String = UUID.randomUUID().toString(),
    var description: String? = null,
    var transactionDate: Long,
    var transactionType: String,
    var value: Double
): Parcelable

/**
 * Map view to domain object
 */
fun TransactionRecordVO.toDomain(): TransactionRecord {
    return TransactionRecord(
        id = id,
        description = description,
        transactionDate = transactionDate,
        transactionType = transactionType,
        value = value
    )
}

/**
 * Map domain to view object
 */
fun TransactionRecord.toVO(): TransactionRecordVO {
    return TransactionRecordVO(
        id = id,
        description = description,
        transactionDate = transactionDate,
        transactionType = transactionType,
        value = value
    )
}