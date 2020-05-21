package com.github.ekamekas.koin.transaction.data.source.transaction_record

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
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
@Entity(tableName = TransactionRecordDB.TABLE_NAME)
data class TransactionRecordDB(
    @PrimaryKey @ColumnInfo(name = ID) val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = DESCRIPTION) var description: String? = null,
    @ColumnInfo(name = TRANSACTION_DATE) var transactionDate: Long,
    @ColumnInfo(name = TRANSACTION_TYPE) var transactionType: String,
    @ColumnInfo(name = VALUE) var value: Double
) {
    companion object {
        const val TABLE_NAME = "transaction_record"
        const val ID = "id"
        const val DESCRIPTION = "description"
        const val TRANSACTION_DATE = "transactionDate"
        const val TRANSACTION_TYPE = "transactionType"
        const val VALUE = "value"
    }
}

/**
 * Map domain to database object
 */
fun TransactionRecord.toDB(): TransactionRecordDB {
    return TransactionRecordDB(
        id = id,
        description = description,
        transactionDate = transactionDate,
        transactionType = transactionType,
        value = value
    )
}

/**
 * Map database to domain object
 */
fun TransactionRecordDB.toDomain(): TransactionRecord {
    return TransactionRecord(
        id = id,
        description = description,
        transactionDate = transactionDate,
        transactionType = transactionType,
        value = value
    )
}