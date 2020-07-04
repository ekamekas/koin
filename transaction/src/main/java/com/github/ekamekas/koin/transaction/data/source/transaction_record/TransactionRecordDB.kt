package com.github.ekamekas.koin.transaction.data.source.transaction_record

import androidx.room.*
import com.github.ekamekas.koin.transaction.data.source.transaction_category.TransactionCategoryDB
import com.github.ekamekas.koin.transaction.data.source.transaction_category.toDomain
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
 * @param transactionCategoryId transaction category identifier of this record
 *
 *
 */
@Entity(
    tableName = TransactionRecordDB.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = TransactionCategoryDB::class,
            parentColumns = [TransactionCategoryDB.ID],
            childColumns = [TransactionRecordDB.TRANSACTION_CATEGORY_ID],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(
            value = [TransactionRecordDB.TRANSACTION_CATEGORY_ID],
            name = "index_${TransactionRecordDB.TABLE_NAME}_${TransactionRecordDB.TRANSACTION_CATEGORY_ID}"
        )
    ]
)
data class TransactionRecordDB(
    @PrimaryKey @ColumnInfo(name = ID) val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = DESCRIPTION) var description: String? = null,
    @ColumnInfo(name = TRANSACTION_DATE) var transactionDate: Long,
    @ColumnInfo(name = TRANSACTION_TYPE) var transactionType: String,
    @ColumnInfo(name = VALUE) var value: Double,
    @ColumnInfo(name = TRANSACTION_CATEGORY_ID) var transactionCategoryId: String? = null
) {
    companion object {
        const val TABLE_NAME = "transaction_record"
        const val ID = "id"
        const val DESCRIPTION = "description"
        const val TRANSACTION_DATE = "transactionDate"
        const val TRANSACTION_TYPE = "transactionType"
        const val VALUE = "value"
        const val TRANSACTION_CATEGORY_ID = "transactionCategoryId"
    }
}

/**
 * Database representation of relation between TransactionCategories
 * support only two level of parent-child realtion
 *
 * @param transactionCategory transaction record
 * @param transactionCategory transaction category
 */
data class TransactionRecordAndCategoryDB(
    @Embedded val transactionRecord: TransactionRecordDB,
    @Relation(
        parentColumn = TransactionRecordDB.TRANSACTION_CATEGORY_ID,
        entityColumn = TransactionCategoryDB.ID
    )
    val transactionCategory: TransactionCategoryDB? = null
)

/**
 * Map domain to database object
 */
fun TransactionRecord.toDB(): TransactionRecordDB {
    return TransactionRecordDB(
        id = id,
        description = description,
        transactionDate = transactionDate,
        transactionType = transactionType,
        transactionCategoryId = transactionCategory?.id,
        value = value
    )
}

/**
 * Map database to domain object
 * because database object only support parent-child relation, then [TransactionRecordDB] expected as root and wont have any parents
 */
fun TransactionRecordDB.toDomain(): TransactionRecord {
    return TransactionRecord(
        id = id,
        description = description,
        transactionDate = transactionDate,
        transactionType = transactionType,
        transactionCategory = null,
        value = value
    )
}

/**
 * Map database object to domain
 * because database object only support parent-child relation, then [TransactionRecordAndCategoryDB] expected as child and will have only one parent
 */
fun TransactionRecordAndCategoryDB.toDomain(): TransactionRecord {
    return TransactionRecord(
        id = transactionRecord.id,
        description = transactionRecord.description,
        transactionDate = transactionRecord.transactionDate,
        transactionType = transactionRecord.transactionType,
        transactionCategory = transactionCategory?.toDomain(),
        value = transactionRecord.value
    )
}