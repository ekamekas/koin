package com.github.ekamekas.koin.transaction.domain.use_case.transaction_record

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
import com.github.ekamekas.koin.transaction.domain.validator.ITransactionValidator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Testing suite for [PutSingleTransactionRecordUseCase]
 */
@ExperimentalCoroutinesApi
class PutSingleTransactionRecordUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var transactionRecordRepository: ITransactionRepository.TransactionRecord
    @Mock
    lateinit var transactionRecordValidator: ITransactionValidator.TransactionRecord

    lateinit var useCase: ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = PutSingleTransactionRecordUseCase(
            dispatcher = coroutineRule.dispatcher,
            transactionRecordRepository = transactionRecordRepository,
            transactionRecordValidator = transactionRecordValidator
        )
    }

    @Test
    fun run_given_validationReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val param = ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase.Param(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            Mockito
                .`when`(transactionRecordValidator.validate(param.transactionRecord))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = useCase.invoke(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun run_given_validationReturnSuccessAndRepositoryReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val param = ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase.Param(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            Mockito
                .`when`(transactionRecordValidator.validate(param.transactionRecord))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordRepository.putTransactionRecord(param.transactionRecord))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = useCase.invoke(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun run_given_validationReturnSuccessAndRepositoryReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase.Param(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            Mockito
                .`when`(transactionRecordValidator.validate(param.transactionRecord))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordRepository.putTransactionRecord(param.transactionRecord))
                .thenReturn(Result.Success(null))
            // act
            val result = useCase.invoke(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test(expected = NotImplementedError::class)
    fun cancel_then_throwNotImplementedError() {
        runBlockingTest {
            // arrange
            // act
            useCase.cancel()
            // assert
        }
    }

}