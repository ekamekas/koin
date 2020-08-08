package com.github.ekamekas.koin.transaction.domain.use_case.transaction_category

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
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
 * Testing suite for [GetTransactionCategoryUseCase]
 */
@ExperimentalCoroutinesApi
class GetTransactionCategoryUseCaseTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var transactionCategoryRepository: ITransactionRepository.TransactionCategory
    private lateinit var useCase: ITransactionUseCase.TransactionCategory.GetTransactionCategoryUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = GetTransactionCategoryUseCase(
            dispatcher = coroutineRule.dispatcher,
            transactionCategoryRepository = transactionCategoryRepository
        )
    }

    @Test
    fun run_when_repositoryReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito.`when`(transactionCategoryRepository.getTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = useCase.invoke(null)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun run_when_repositoryReturnSuccess_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryRepository.getTransactionCategory())
                .thenReturn(Result.Success(data))
            // act
            val result = useCase.invoke(null)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
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