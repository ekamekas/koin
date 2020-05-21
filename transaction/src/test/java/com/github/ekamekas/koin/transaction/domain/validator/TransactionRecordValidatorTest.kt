package com.github.ekamekas.koin.transaction.domain.validator

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Testing suite for [TransactionRecordValidator]
 */
@ExperimentalCoroutinesApi
class TransactionRecordValidatorTest {

    private lateinit var validator: ITransactionValidator.TransactionRecord

    @Before
    fun setup() {
        validator = TransactionRecordValidator()
    }

    @Test
    fun validateId_when_idIsEmpty_then_returnError() {
        runBlockingTest {
            // arrange
            val param = ""
            // act
            val result = validator.validateId(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validateId_when_idIsBlank_then_returnError() {
        runBlockingTest {
            // arrange
            val param = "     "
            // act
            val result = validator.validateId(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validateId_when_idIsCorrect_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = "test"
            // act
            val result = validator.validateId(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun validateDescription_when_descriptionIsEmpty_then_returnError() {
        runBlockingTest {
            // arrange
            val param = ""
            // act
            val result = validator.validateDescription(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validateDescription_when_descriptionIsBlank_then_returnError() {
        runBlockingTest {
            // arrange
            val param = "   "
            // act
            val result = validator.validateDescription(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validateDescription_when_descriptionIsNull_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = null
            // act
            val result = validator.validateDescription(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun validateDescription_when_descriptionIsCorrect_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = "test"
            // act
            val result = validator.validateDescription(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun validateTransactionDate_when_dateIsPast_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = System.currentTimeMillis() - 3600000  // 1 hour before now
            // act
            val result = validator.validateTransactionDate(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun validateTransactionDate_when_dateIsFuture_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = System.currentTimeMillis() + 3600000  // 1 hour after now
            // act
            val result = validator.validateTransactionDate(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun validateTransactionType_when_typeIsEmpty_then_returnError() {
        runBlockingTest {
            // arrange
            val param = ""
            // act
            val result = validator.validateTransactionType(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validateTransactionType_when_typeIsBlank_then_returnError() {
        runBlockingTest {
            // arrange
            val param = "  "
            // act
            val result = validator.validateTransactionType(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validateTransactionType_when_typeIsNotCorrect_then_returnError() {
        runBlockingTest {
            // arrange
            val param = "test"
            // act
            val result = validator.validateTransactionType(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validateTransactionType_when_typeIsCorrect_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = TransactionType.EXPENSE
            // act
            val result = validator.validateTransactionType(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun validateValue_when_valueIsNegativeNumber_then_returnError() {
        runBlockingTest {
            // arrange
            val param = Double.NEGATIVE_INFINITY
            // act
            val result = validator.validateValue(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validateValue_when_valueIsZero_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = 0.0
            // act
            val result = validator.validateValue(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun validateValue_when_valueIsPositiveNumber_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = Double.POSITIVE_INFINITY
            // act
            val result = validator.validateValue(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun validate_given_validateIdReturnErrorElseSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val param = TransactionRecord(
                id = "",
                description = null,
                transactionDate = System.currentTimeMillis(),
                transactionType = TransactionType.INCOME,
                value = Double.POSITIVE_INFINITY
            )  // empty will make validateId error
            // act
            val result = validator.validate(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validate_given_validateDescriptionReturnErrorElseSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val param = TransactionRecord(
                id = "test",
                description = "",
                transactionDate = System.currentTimeMillis(),
                transactionType = TransactionType.INCOME,
                value = Double.POSITIVE_INFINITY
            )  // empty will make validateDescription error
            // act
            val result = validator.validate(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validate_given_validateTransactionTypeReturnErrorElseSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val param = TransactionRecord(
                id = "test",
                description = null,
                transactionDate = System.currentTimeMillis(),
                transactionType = "",
                value = Double.POSITIVE_INFINITY
            )  // empty will make validateTransactionType error
            // act
            val result = validator.validate(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validate_given_validateValueReturnErrorElseSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val param = TransactionRecord(
                id = "test",
                description = null,
                transactionDate = System.currentTimeMillis(),
                transactionType = TransactionType.INCOME,
                value = Double.NEGATIVE_INFINITY
            )  // negative number will make validateValue error
            // act
            val result = validator.validate(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun validate_given_allSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = TransactionRecord(
                id = "test",
                description = null,
                transactionDate = System.currentTimeMillis(),
                transactionType = TransactionType.INCOME,
                value = Double.POSITIVE_INFINITY
            )  // empty will make validateId error
            // act
            val result = validator.validate(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

}