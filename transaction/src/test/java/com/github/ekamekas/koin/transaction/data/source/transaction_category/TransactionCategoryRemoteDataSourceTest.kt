package com.github.ekamekas.koin.transaction.data.source.transaction_category

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
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
 * Testing suite for [TransactionCategoryRemoteDataSource]
 */
@ExperimentalCoroutinesApi
class TransactionCategoryRemoteDataSourceTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var transactionCategoryService: TransactionCategoryService
    private lateinit var dataSource: ITransactionDataSource.TransactionCategory

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        dataSource = TransactionCategoryRemoteDataSource(
            dispatcher = coroutineRule.dispatcher,
            transactionCategoryService = transactionCategoryService
        )
    }

    @Test(expected = NotImplementedError::class)
    fun deleteTransactionCategory_then_throwNotImplementedError() {
        runBlockingTest {
            // arrange
            // act
            dataSource.deleteTransactionCategory()
            // assert
        }
    }
    @Test(expected = NotImplementedError::class)
    fun deleteTransactionCategory_given_id_then_throwNotImplementedError() {
        runBlockingTest {
            // arrange
            val id = "test"
            // act
            dataSource.deleteTransactionCategory(id)
            // assert
        }
    }

    @Test(expected = NotImplementedError::class)
    fun deleteTransactionCategoryByCategory_given_id_then_throwNotImplementedError() {
        runBlockingTest {
            // arrange
            val id = "test"
            // act
            dataSource.deleteTransactionCategoryByCategory(id)
            // assert
        }
    }

    @Test
    fun fetchTransactionCategory_when_throwException_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito.`when`(transactionCategoryService.fetch())
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.fetchTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategory_when_success_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategoryDTO(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryService.fetch())
                .thenReturn(data)
            // act
            val result = dataSource.fetchTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data.map { it.toDomain() }, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun fetchTransactionCategory_given_id_when_throwException_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(transactionCategoryService.fetch(id))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.fetchTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategory_given_id_when_success_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = TransactionCategoryDTO(
                id = id,
                name = "test",
                image = null
            )
            Mockito.`when`(transactionCategoryService.fetch(id))
                .thenReturn(data)
            // act
            val result = dataSource.fetchTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data.toDomain(), (result as? Result.Success)?.data)
        }
    }

    @Test
    fun fetchTransactionCategoryByCategory_given_id_when_throwException_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(transactionCategoryService.fetchByCategory(id))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.fetchTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategoryByCategory_given_id_when_success_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val id = "teset"
            val data = listOf(
                TransactionCategoryDTO(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryService.fetchByCategory(id))
                .thenReturn(data)
            // act
            val result = dataSource.fetchTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data.map { it.toDomain() }, (result as? Result.Success)?.data)
        }
    }

    @Test(expected = NotImplementedError::class)
    fun upsertTransactionCategory_given_data_then_throwNotImplementedError() {
        runBlockingTest {
            // arrange
            val data = TransactionCategory(
                name = "test",
                image = null
            )
            // act
            dataSource.upsertTransactionCategory(data)
            // assert
        }
    }
    @Test(expected = NotImplementedError::class)
    fun upsertTransactionCategory_given_listData_then_throwNotImplementedError() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            // act
            dataSource.upsertTransactionCategory(data)
            // assert
        }
    }

}