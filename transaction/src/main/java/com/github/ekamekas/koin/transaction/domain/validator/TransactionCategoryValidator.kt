package com.github.ekamekas.koin.transaction.domain.validator

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import com.github.ekamekas.koin.transaction.ext.fromBase64
import javax.inject.Inject

/**
 * Implementation of [ITransactionValidator.TransactionCategory]
 */
class TransactionCategoryValidator @Inject constructor(): ITransactionValidator.TransactionCategory {

    override suspend fun validate(param: TransactionCategory): Result<Nothing?> {
        return validateId(param.id)
            .mapRightSuspend {
                validateName(param.name)
            }
            .mapRightSuspend {
                validateDescription(param.description)
            }
            .mapRightSuspend {
                validateImage(param.image)
            }
            .mapRightSuspend {
                validateParentCategory(param.parentCategory)
            }
    }

    override suspend fun validateId(param: String): Result<Nothing?> {
        return if(param.isBlank()) {
            Result.Error(IllegalStateException("id must not empty or blank"))
        } else {
            Result.Success(null)
        }
    }

    override suspend fun validateName(param: String): Result<Nothing?> {
        return if(param.isBlank()) {
            Result.Error(IllegalStateException("name must not empty or blank"))
        } else {
            Result.Success(null)
        }
    }

    override suspend fun validateDescription(param: String?): Result<Nothing?> {
        return when {
            param == null -> {
                Result.Success(null)
            }
            param.isBlank() -> {
                Result.Error(IllegalStateException("description must not empty or blank"))
            }
            else -> {
                Result.Success(null)
            }
        }
    }

    override suspend fun validateImage(param: String?): Result<Nothing?> {
        return when {
            param == null -> {
                Result.Success(null)
            }
            param.isBlank() -> {
                Result.Error(IllegalStateException("image must not empty or blank"))
            }
            else -> {
                Result.Success(null)
            }
        }
    }

    override suspend fun validateParentCategory(param: TransactionCategory?): Result<Nothing?> {
        return if(param == null) {
            Result.Success(null)
        } else validate(param)
    }
}