package com.github.ekamekas.koin.transaction.data.source.transaction_category

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.data.source.TransactionDatabase
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
 * Testing suite for [TransactionCategoryLocalDataSource]
 */
@ExperimentalCoroutinesApi
class TransactionCategoryLocalDataSourceTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var database: TransactionDatabase
    @Mock
    lateinit var transactionCategoryDAO: TransactionCategoryDAO
    private lateinit var dataSource: ITransactionDataSource.TransactionCategory

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(database.transactionCategoryDAO())
            .thenReturn(transactionCategoryDAO)
        dataSource = TransactionCategoryLocalDataSource(
            dispatcher = coroutineRule.dispatcher,
            database = database
        )
    }

    @Test
    fun deleteTransactionCategory_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito.`when`(database.transactionCategoryDAO().delete())
                .thenReturn(0)
            // act
            val result = dataSource.deleteTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito.`when`(database.transactionCategoryDAO().delete())
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.deleteTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            Mockito.`when`(database.transactionCategoryDAO().delete())
                .thenReturn(1)
            // act
            val result = dataSource.deleteTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionCategoryDAO().delete(id))
                .thenReturn(0)
            // act
            val result = dataSource.deleteTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionCategoryDAO().delete(id))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.deleteTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionCategoryDAO().delete(id))
                .thenReturn(1)
            // act
            val result = dataSource.deleteTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun deleteTransactionCategoryByCategory_given_id_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionCategoryDAO().delete(id))
                .thenReturn(0)
            // act
            val result = dataSource.deleteTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategoryByCategory_given_id_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionCategoryDAO().delete(id))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.deleteTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategoryCategory_given_id_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionCategoryDAO().delete(id))
                .thenReturn(1)
            // act
            val result = dataSource.deleteTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun fetchTransactionCategory_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito.`when`(database.transactionCategoryDAO().fetch())
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
                TransactionCategoryAndParentDB(
                    transactionCategory = TransactionCategoryDB(
                        name = "test",
                        image = null
                    ),
                    parentCategory = null
                )
            )
            Mockito.`when`(database.transactionCategoryDAO().fetch())
                .thenReturn(data)
            // act
            val result = dataSource.fetchTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data.map { it.toDomain() }, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun fetchTransactionCategory_given_id_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionCategoryDAO().fetch(id))
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
            val data = TransactionCategoryAndParentDB(
                transactionCategory = TransactionCategoryDB(
                    id = id,
                    name = "test",
                    image = null
                ),
                parentCategory = null
            )
            Mockito.`when`(database.transactionCategoryDAO().fetch(id))
                .thenReturn(data)
            // act
            val result = dataSource.fetchTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data.toDomain(), (result as? Result.Success)?.data)
        }
    }

    @Test
    fun fetchTransactionCategoryByCategory_given_id_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionCategoryDAO().fetchByCategory(id))
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
            val id = "test"
            val data = listOf(
                TransactionCategoryAndParentDB(
                    transactionCategory = TransactionCategoryDB(
                        name = "child",
                        image = null,
                        parentCategoryId = id
                    ),
                    parentCategory = TransactionCategoryDB(
                        id = id,
                        name = "parent",
                        image = null
                    )
                )
            )
            Mockito.`when`(database.transactionCategoryDAO().fetchByCategory(id))
                .thenReturn(data)
            // act
            val result = dataSource.fetchTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data.map { it.toDomain() }, (result as? Result.Success)?.data)
        }
    }

    @Test
    fun upsertTransactionCategory_given_data_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val param = TransactionCategory(
                name = "test",
                image = null
            )
            Mockito.`when`(database.transactionCategoryDAO().upsert(param.toDB()))
                .thenReturn(0)
            // act
            val result = dataSource.upsertTransactionCategory(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun upsertTransactionRecord_given_data_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val param = TransactionCategory(
                name = "test",
                image = null
            )
            Mockito.`when`(database.transactionCategoryDAO().upsert(param.toDB()))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.upsertTransactionCategory(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun upsertTransactionRecord_given_data_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = TransactionCategory(
                name = "test",
                image = null
            )
            Mockito.`when`(database.transactionCategoryDAO().upsert(param.toDB()))
                .thenReturn(1)
            // act
            val result = dataSource.upsertTransactionCategory(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun upsertTransactionRecord_given_listData_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val param = listOf(
                    TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(database.transactionCategoryDAO().upsert(param.map { it.toDB() }))
                .thenReturn(0)
            // act
            val result = dataSource.upsertTransactionCategory(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun upsertTransactionRecord_given_listData_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val param = listOf(
                    TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(database.transactionCategoryDAO().upsert(param.map { it.toDB() }))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.upsertTransactionCategory(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun upsertTransactionRecord_given_listData_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = listOf(
                    TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(database.transactionCategoryDAO().upsert(param.map { it.toDB() }))
                .thenReturn(1)
            // act
            val result = dataSource.upsertTransactionCategory(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

}