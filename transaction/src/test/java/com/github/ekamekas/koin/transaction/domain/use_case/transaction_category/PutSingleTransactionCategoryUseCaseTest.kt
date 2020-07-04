package com.github.ekamekas.koin.transaction.domain.use_case.transaction_category

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
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
 * Testing suite for [PutSingleTransactionCategoryUseCase]
 */
@ExperimentalCoroutinesApi
class PutSingleTransactionCategoryUseCaseTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var transactionCategoryRepository: ITransactionRepository.TransactionCategory
    @Mock
    lateinit var transactionCategoryValidator: ITransactionValidator.TransactionCategory
    private lateinit var useCase: ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = PutSingleTransactionCategoryUseCase(
            dispatcher = coroutineRule.dispatcher,
            transactionCategoryRepository = transactionCategoryRepository,
            transactionCategoryValidator = transactionCategoryValidator
        )
    }

    @Test
    fun run_when_validatorReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val param = ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase.Param(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryValidator.validate(param.transactionCategory))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = useCase.invoke(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }

    @Test
    fun run_when_validatorReturnSuccessAndRepositoryReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val param = ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase.Param(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryValidator.validate(param.transactionCategory))
                .thenReturn(Result.Success(null))
            Mockito.`when`(transactionCategoryRepository.putTransactionCategory(param.transactionCategory))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = useCase.invoke(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun run_when_validatorReturnSuccessAndRepositoryReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase.Param(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryValidator.validate(param.transactionCategory))
                .thenReturn(Result.Success(null))
            Mockito.`when`(transactionCategoryRepository.putTransactionCategory(param.transactionCategory))
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