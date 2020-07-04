package com.github.ekamekas.koin.transaction.presentation.view_object

import android.os.Parcelable
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * View object representation of [TransactionCategory]
 *
 * @param isDeleted flat to indicate object is deleted
 */
@Parcelize
data class TransactionCategoryVO(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val image: String?,
    val parentCategory: TransactionCategoryVO? = null,
    var isDeleted: Boolean = false
): Parcelable

/**
 * Map view to domain object
 */
fun TransactionCategoryVO.toDomain(): TransactionCategory {
    return TransactionCategory(
        id = id,
        name = name,
        description = description,
        image = image,
        parentCategory = parentCategory?.toDomain()
    )
}

/**
 * Map domain to view object
 */
fun TransactionCategory.toVO(): TransactionCategoryVO {
    return TransactionCategoryVO(
        id = id,
        name = name,
        description = description,
        image = image,
        parentCategory = parentCategory?.toVO()
    )
}