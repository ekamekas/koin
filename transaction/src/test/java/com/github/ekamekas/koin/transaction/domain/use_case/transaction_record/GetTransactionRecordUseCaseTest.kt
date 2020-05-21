package com.github.ekamekas.koin.transaction.domain.use_case.transaction_record

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import com.github.ekamekas.koin.transaction.domain.use_case.ITransactionUseCase
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
 * Testing suite for [GetTransactionRecordUseCase]
 */
@ExperimentalCoroutinesApi
class GetTransactionRecordUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var transactionRecordRepository: ITransactionRepository.TransactionRecord

    private lateinit var useCase: ITransactionUseCase.TransactionRecord.GetTransactionRecordUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = GetTransactionRecordUseCase(
            dispatcher = coroutineRule.dispatcher,
            transactionRecordRepository = transactionRecordRepository
        )
    }

    @Test
    fun run_given_transactionRecordRepositoryReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionRecordRepository.getTransactionRecord())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = useCase.invoke(null)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun run_given_transactionRecordRepositoryReturnSuccessAndData_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            Mockito
                .`when`(transactionRecordRepository.getTransactionRecord())
                .thenReturn(Result.Success(data))
            // act
            val result = useCase.invoke(null)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }

    @Test(expected = NotImplementedError::class)
    fun cancel_then_throwNotImplemented() {
        runBlockingTest {
            // arrange
            // act
            useCase.cancel()
            // assert
        }
    }

}