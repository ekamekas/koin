package com.github.ekamekas.koin.transaction.data.repository

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
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
 * Testing suite for [TransactionRecordRepository]
 */
@ExperimentalCoroutinesApi
class TransactionRecordRepositoryTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var transactionRecordCacheDataSource: ITransactionDataSource.TransactionRecord
    @Mock
    lateinit var transactionRecordLocalDataSource: ITransactionDataSource.TransactionRecord

    private lateinit var repository: ITransactionRepository.TransactionRecord

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = TransactionRecordRepository(
            dispatcher = coroutineRule.dispatcher,
            transactionRecordCacheDataSource = transactionRecordCacheDataSource,
            transactionRecordLocalDataSource = transactionRecordLocalDataSource
        )
    }

    @Test
    fun deleteTransactionRecord_when_localDataSourceReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionRecordLocalDataSource.deleteTransactionRecord())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_when_localDataSourceReturnSuccess_then_invokeCacheDataSourceOnce() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionRecordLocalDataSource.deleteTransactionRecord())
                .thenReturn(Result.Success(null))
            // act
            repository.deleteTransactionRecord()
            // assert
            Mockito.verify(transactionRecordCacheDataSource, Mockito.times(1)).deleteTransactionRecord()
        }
    }
    @Test
    fun deleteTransactionRecord_when_localDataSourceReturnSuccessAndCacheDataSourceReturnError_then_returnSuccess() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionRecordLocalDataSource.deleteTransactionRecord())
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordCacheDataSource.deleteTransactionRecord())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionRecord_when_localDataSourceReturnSuccessAndCacheDataSourceReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionRecordLocalDataSource.deleteTransactionRecord())
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordCacheDataSource.deleteTransactionRecord())
                .thenReturn(Result.Success(null))
            // act
            val result = repository.deleteTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id__when_localDataSourceReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionRecordLocalDataSource.deleteTransactionRecord(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id_when_localDataSourceReturnSuccess_then_invokeCacheDataSourceOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionRecordLocalDataSource.deleteTransactionRecord(id))
                .thenReturn(Result.Success(null))
            // act
            repository.deleteTransactionRecord(id)
            // assert
            Mockito.verify(transactionRecordCacheDataSource, Mockito.times(1)).deleteTransactionRecord(id)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id_when_localDataSourceReturnSuccessAndCacheDataSourceReturnError_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionRecordLocalDataSource.deleteTransactionRecord(id))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordCacheDataSource.deleteTransactionRecord(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id_when_localDataSourceReturnSuccessAndCacheDataSourceReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionRecordLocalDataSource.deleteTransactionRecord(id))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordCacheDataSource.deleteTransactionRecord(id))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.deleteTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun getTransactionRecord_when_cacheDataSourceReturnError_then_invokeLocalDataSourceOnce() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionRecordCacheDataSource.fetchTransactionRecord())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            repository.getTransactionRecord()
            // assert
            Mockito.verify(transactionRecordLocalDataSource, Mockito.times(1)).fetchTransactionRecord()
        }
    }
    @Test
    fun getTransactionRecord_when_cacheDataSourceReturnErrorAndLocalDataSourceReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionRecordCacheDataSource.fetchTransactionRecord())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito
                .`when`(transactionRecordLocalDataSource.fetchTransactionRecord())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.getTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun getTransactionRecord_when_cacheDataSourceReturnSuccess_then_returnSuccessAndData() {
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
                .`when`(transactionRecordCacheDataSource.fetchTransactionRecord())
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun getTransactionRecord_when_cacheDataSourceReturnErrorAndLocalDataSourceReturnSuccess_then_returnSuccessAndData() {
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
                .`when`(transactionRecordCacheDataSource.fetchTransactionRecord())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito
                .`when`(transactionRecordLocalDataSource.fetchTransactionRecord())
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun getTransactionRecord_given_id_when_cacheDataSourceReturnError_then_invokeLocalDataSourceOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionRecordCacheDataSource.fetchTransactionRecord(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            repository.getTransactionRecord(id)
            // assert
            Mockito.verify(transactionRecordLocalDataSource, Mockito.times(1)).fetchTransactionRecord(id)
        }
    }
    @Test
    fun getTransactionRecord_given_id_when_cacheDataSourceReturnErrorAndLocalDataSourceReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionRecordCacheDataSource.fetchTransactionRecord(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito
                .`when`(transactionRecordLocalDataSource.fetchTransactionRecord(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.getTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun getTransactionRecord_given_id_when_cacheDataSourceReturnSuccess_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito
                .`when`(transactionRecordCacheDataSource.fetchTransactionRecord(id))
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun getTransactionRecord_given_id_when_cacheDataSourceReturnErrorAndLocalDataSourceReturnSuccess_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito
                .`when`(transactionRecordCacheDataSource.fetchTransactionRecord(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito
                .`when`(transactionRecordLocalDataSource.fetchTransactionRecord(id))
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }

    @Test
    fun putTransactionRecord_given_data_when_localDataSourceReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val data = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito
                .`when`(transactionRecordLocalDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Error(RuntimeException("expected behavior")))
            // act
            val result = repository.putTransactionRecord(data)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun putTransactionRecord_given_data_when_localDataSourceReturnSuccess_then_invokeCacheDataSourceOnce() {
        runBlockingTest {
            // arrange
            val data = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito
                .`when`(transactionRecordLocalDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Success(null))
            // act
            repository.putTransactionRecord(data)
            // assert
            Mockito.verify(transactionRecordCacheDataSource, Mockito.times(1)).upsertTransactionRecord(data)
        }
    }
    @Test
    fun putTransactionRecord_given_data_when_localDataSourceReturnSuccessAndCacheDataSourceReturnError_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val data = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito
                .`when`(transactionRecordLocalDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordCacheDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Error(RuntimeException("expected behavior")))
            // act
            val result = repository.putTransactionRecord(data)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun putTransactionRecord_given_data_when_localDataSourceReturnSuccessAndCacheDataSourceReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val data = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            Mockito
                .`when`(transactionRecordLocalDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordCacheDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.putTransactionRecord(data)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun putTransactionRecord_given_listData_when_localDataSourceReturnError_then_returnError() {
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
                .`when`(transactionRecordLocalDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Error(RuntimeException("expected behavior")))
            // act
            val result = repository.putTransactionRecord(data)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun putTransactionRecord_given_listData_when_localDataSourceReturnSuccess_then_invokeCacheDataSourceOnce() {
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
                .`when`(transactionRecordLocalDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Success(null))
            // act
            repository.putTransactionRecord(data)
            // assert
            Mockito.verify(transactionRecordCacheDataSource, Mockito.times(1)).upsertTransactionRecord(data)
        }
    }
    @Test
    fun putTransactionRecord_given_listData_when_localDataSourceReturnSuccessAndCacheDataSourceReturnError_then_returnSuccess() {
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
                .`when`(transactionRecordLocalDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordCacheDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Error(RuntimeException("expected behavior")))
            // act
            val result = repository.putTransactionRecord(data)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun putTransactionRecord_given_listData_when_localDataSourceReturnSuccessAndCacheDataSourceReturnSuccess_then_returnSuccess() {
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
                .`when`(transactionRecordLocalDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionRecordCacheDataSource.upsertTransactionRecord(data))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.putTransactionRecord(data)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

}