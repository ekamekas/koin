package com.github.ekamekas.koin.transaction.data.source.transaction_category

import androidx.room.*
import com.github.ekamekas.koin.transaction.data.source.transaction_record.TransactionRecordDB
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import java.util.*

/**
 * Database representation of TransactionCategory
 *
 * @param id identifier
 * @param name name of transaction category
 * @param description description of transaction category
 * @param image base64 representation of transaction category image
 * @param parentCategoryId parent id of this transaction category
 */
@Entity(
    tableName = TransactionCategoryDB.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = TransactionCategoryDB::class,
            parentColumns = [TransactionCategoryDB.ID],
            childColumns = [TransactionCategoryDB.PARENT_CATEGORY_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = [TransactionCategoryDB.PARENT_CATEGORY_ID],
            name = "index_${TransactionCategoryDB.TABLE_NAME}_${TransactionCategoryDB.PARENT_CATEGORY_ID}"
        )
    ]
)
data class TransactionCategoryDB(
    @PrimaryKey @ColumnInfo(name = ID) val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = NAME) var name: String,
    @ColumnInfo(name = DESCRIPTION) var description: String? = null,
    @ColumnInfo(name = IMAGE) var image: String?,
    @ColumnInfo(name = PARENT_CATEGORY_ID) var parentCategoryId: String? = null
) {

    companion object {
        const val TABLE_NAME = "transaction_category"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
        const val PARENT_CATEGORY_ID = "parentCategoryId"
    }

}

/**
 * Database representation of relation between TransactionCategories
 * support only two level of parent-child realtion
 *
 * @param transactionCategory transaction category
 * @param parentCategory transaction category
 */
data class TransactionCategoryAndParentDB(
    @Embedded val transactionCategory: TransactionCategoryDB,
    @Relation(
        parentColumn = TransactionCategoryDB.ID,
        entityColumn = TransactionCategoryDB.PARENT_CATEGORY_ID
    )
    val parentCategory: TransactionCategoryDB?
)

/**
 * Map domain to database object
 */
fun TransactionCategory.toDB(): TransactionCategoryDB {
    return TransactionCategoryDB(
        id = id,
        name = name,
        description = description,
        image = image,
        parentCategoryId = parentCategory?.id
    )
}

/**
 * Map domain to database object
 * because database object only support parent-child relation, then [TransactionCategoryDB] expected as root and wont have any parents
 */
fun TransactionCategoryDB.toDomain(): TransactionCategory {
    return TransactionCategory(
        id = id,
        name = name,
        description = description,
        image = image,
        parentCategory = null
    )
}

/**
 * Map database object to domain
 * because database object only support parent-child relation, then [TransactionCategoryAndParentDB] expected as child and will have only one parent
 */
fun TransactionCategoryAndParentDB.toDomain(): TransactionCategory {
    return TransactionCategory(
        id = transactionCategory.id,
        name = transactionCategory.name,
        description = transactionCategory.description,
        image = transactionCategory.image,
        parentCategory = parentCategory?.toDomain()
    )
}