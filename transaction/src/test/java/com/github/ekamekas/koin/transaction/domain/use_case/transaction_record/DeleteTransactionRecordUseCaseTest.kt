package com.github.ekamekas.koin.transaction.domain.use_case.transaction_record

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
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
 * Testing suite for [DeleteTransactionRecordUseCase]
 */
@ExperimentalCoroutinesApi
class DeleteTransactionRecordUseCaseTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var transactionRecordRepository: ITransactionRepository.TransactionRecord
    private lateinit var useCase: ITransactionUseCase.TransactionRecord.DeleteTransactionRecordUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = DeleteTransactionRecordUseCase(
            dispatcher = coroutineRule.dispatcher,
            transactionRecordRepository = transactionRecordRepository
        )
    }

    @Test
    fun run_when_repositoryReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val param = ITransactionUseCase.TransactionRecord.DeleteTransactionRecordUseCase.Param(
                id = "test"
            )
            Mockito.`when`(transactionRecordRepository.deleteTransactionRecord(param.id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = useCase.invoke(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }

    @Test
    fun run_when_repositoryReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = ITransactionUseCase.TransactionRecord.DeleteTransactionRecordUseCase.Param(
                id = "test"
            )
            Mockito.`when`(transactionRecordRepository.deleteTransactionRecord(param.id))
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