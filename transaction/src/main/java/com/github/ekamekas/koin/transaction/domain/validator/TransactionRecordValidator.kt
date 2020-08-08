package com.github.ekamekas.koin.transaction.domain.validator

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import javax.inject.Inject

/**
 * Implementation of [ITransactionValidator.TransactionRecord]
 */
class TransactionRecordValidator @Inject constructor(): ITransactionValidator.TransactionRecord {

    override suspend fun validate(param: TransactionRecord): Result<Nothing?> {
        return validateId(param.id)
            .mapRightSuspend {
                validateDescription(param.description)
            }
            .mapRightSuspend {
                validateTransactionDate(param.transactionDate)
            }
            .mapRightSuspend {
                validateTransactionType(param.transactionType)
            }
            .mapRightSuspend {
                validateValue(param.value)
            }
    }

    override suspend fun validateId(param: String): Result<Nothing?> {
        return if(param.isBlank()) {
            Result.Error(IllegalStateException("id must not empty or blank"))
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

    override suspend fun validateTransactionDate(param: Long): Result<Nothing?> {
        return Result.Success(null)
    }

    override suspend fun validateTransactionType(param: String): Result<Nothing?> {
        return when {
            param.isBlank() -> {
                Result.Error(IllegalStateException("transaction type must not empty or blank"))
            }
            param != TransactionType.EXPENSE && param != TransactionType.INCOME -> {
                Result.Error(IllegalStateException("transaction type must in the TransactionType enum"))
            }
            else -> {
                Result.Success(null)
            }
        }
    }

    override suspend fun validateValue(param: Double): Result<Nothing?> {
        return if(param < 0.0) {
            Result.Error(IllegalStateException("value must not be negative number"))
        } else {
            Result.Success(null)
        }
    }
}