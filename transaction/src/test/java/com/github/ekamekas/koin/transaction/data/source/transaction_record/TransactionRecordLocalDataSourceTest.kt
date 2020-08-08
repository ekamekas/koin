package com.github.ekamekas.koin.transaction.data.source.transaction_record

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.data.source.TransactionDatabase
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
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
 * Testing suite for [TransactionRecordLocalDataSource]
 */
@ExperimentalCoroutinesApi
class TransactionRecordLocalDataSourceTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var database: TransactionDatabase
    @Mock
    lateinit var transactionRecordDAO: TransactionRecordDAO
    private lateinit var dataSource: ITransactionDataSource.TransactionRecord

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(database.transactionRecordDAO())
            .thenReturn(transactionRecordDAO)
        dataSource = TransactionRecordLocalDataSource(
            dispatcher = coroutineRule.dispatcher,
            database = database
        )
    }

    @Test
    fun deleteTransactionRecord_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito.`when`(database.transactionRecordDAO().delete())
                .thenReturn(0)
            // act
            val result = dataSource.deleteTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito.`when`(database.transactionRecordDAO().delete())
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.deleteTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            Mockito.`when`(database.transactionRecordDAO().delete())
                .thenReturn(1)
            // act
            val result = dataSource.deleteTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionRecordDAO().delete(id))
                .thenReturn(0)
            // act
            val result = dataSource.deleteTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionRecordDAO().delete(id))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.deleteTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionRecordDAO().delete(id))
                .thenReturn(1)
            // act
            val result = dataSource.deleteTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun fetchTransactionRecord_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito.`when`(database.transactionRecordDAO().fetch())
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.fetchTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionRecord_when_success_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionRecordAndCategoryDB(
                    transactionRecord = TransactionRecordDB(
                        transactionDate = 0L,
                        transactionType = TransactionType.INCOME,
                        value = 0.0
                    )
                )
            )
            Mockito.`when`(database.transactionRecordDAO().fetch())
                .thenReturn(data)
            // act
            val result = dataSource.fetchTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data.map { it.toDomain() }, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun fetchTransactionRecord_given_id_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(database.transactionRecordDAO().fetch(id))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.fetchTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionRecord_given_id_when_success_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = TransactionRecordAndCategoryDB(
                transactionRecord = TransactionRecordDB(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            Mockito.`when`(database.transactionRecordDAO().fetch(id))
                .thenReturn(data)
            // act
            val result = dataSource.fetchTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data.toDomain(), (result as? Result.Success)?.data)
        }
    }

    @Test
    fun upsertTransactionRecord_given_data_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val param = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito.`when`(database.transactionRecordDAO().upsert(param.toDB()))
                .thenReturn(0)
            // act
            val result = dataSource.upsertTransactionRecord(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun upsertTransactionRecord_given_data_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val param = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito.`when`(database.transactionRecordDAO().upsert(param.toDB()))
                .thenThrow(RuntimeException("excepted error"))
            // act
            val result = dataSource.upsertTransactionRecord(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun upsertTransactionRecord_given_data_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito.`when`(database.transactionRecordDAO().upsert(param.toDB()))
                .thenReturn(1)
            // act
            val result = dataSource.upsertTransactionRecord(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun upsertTransactionRecord_given_listData_when_notSuccess_then_returnError() {
        runBlockingTest {
            // arrange
            val param = listOf(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            Mockito.`when`(database.transactionRecordDAO().upsert(param.map { it.toDB() }))
                .thenReturn(0)
            // act
            val result = dataSource.upsertTransactionRecord(param)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun upsertTransactionRecord_given_listData_when_thrownException_then_returnError() {
        runBlockingTest {
            // arrange
            val data = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito.`when`(database.transactionRecordDAO().upsert(data.toDB()))
                .thenThrow(RuntimeException("expected error"))
            // act
            val result = dataSource.upsertTransactionRecord(data)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun upsertTransactionRecord_given_listData_when_success_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val param = listOf(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            Mockito.`when`(database.transactionRecordDAO().upsert(param.map { it.toDB() }))
                .thenReturn(1)
            // act
            val result = dataSource.upsertTransactionRecord(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

}