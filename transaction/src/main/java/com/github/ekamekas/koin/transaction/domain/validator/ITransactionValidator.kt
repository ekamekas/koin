package com.github.ekamekas.koin.transaction.domain.validator

import com.github.ekamekas.baha.core.domain.entity.Result

interface ITransactionValidator {

    /**
     * Contract defined for transaction category validator
     */
    interface TransactionCategory {
        suspend fun validate(param: com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory): Result<Nothing?>
        suspend fun validateId(param: String): Result<Nothing?>
        suspend fun validateName(param: String): Result<Nothing?>
        suspend fun validateDescription(param: String?): Result<Nothing?>
        suspend fun validateImage(param: String?): Result<Nothing?>
        suspend fun validateParentCategory(param: com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory?): Result<Nothing?>
    }

    /**
     * Contract defined for transaction record validator
     */
    interface TransactionRecord {
        suspend fun validate(param: com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord): Result<Nothing?>
        suspend fun validateId(param: String): Result<Nothing?>
        suspend fun validateDescription(param: String?): Result<Nothing?>
        suspend fun validateTransactionDate(param: Long): Result<Nothing?>
        suspend fun validateTransactionType(param: String): Result<Nothing?>
        suspend fun validateValue(param: Double): Result<Nothing?>
    }

}