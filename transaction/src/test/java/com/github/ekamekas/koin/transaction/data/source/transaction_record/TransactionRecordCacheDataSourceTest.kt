package com.github.ekamekas.koin.transaction.data.source.transaction_record

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.domain.entity.TransactionRecord
import com.github.ekamekas.koin.transaction.domain.entity.TransactionType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Testing suite for [TransactionRecordCacheDataSource]
 */
@ExperimentalCoroutinesApi
class TransactionRecordCacheDataSourceTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    private val expirationOffsetMillis = 2000L  // 0ms

    @Test
    fun deleteTransactionRecord_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            // act
            val result = dataSource.deleteTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val data = listOf(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            dataSource.upsertTransactionRecord(data)
            // act
            val fetchBeforeDeleteResult = dataSource.fetchTransactionRecord()
            val result = dataSource.deleteTransactionRecord()
            val fetchAfterDeleteResult = dataSource.fetchTransactionRecord()
            // assert
            Assert.assertTrue(fetchBeforeDeleteResult is Result.Success)
            Assert.assertEquals(data.size, (fetchBeforeDeleteResult as? Result.Success)?.data?.size)
            Assert.assertTrue(result is Result.Success)
            Assert.assertTrue(fetchAfterDeleteResult is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            // act
            val result = dataSource.deleteTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionRecord_given_id_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            val data = TransactionRecord(
                id = id,
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            dataSource.upsertTransactionRecord(data)
            // act
            val fetchBeforeDeleteResult = dataSource.fetchTransactionRecord(id)
            val result = dataSource.deleteTransactionRecord(id)
            val fetchAfterDeleteResult = dataSource.fetchTransactionRecord(id)
            // assert
            Assert.assertTrue(fetchBeforeDeleteResult is Result.Success)
            Assert.assertEquals(data, (fetchBeforeDeleteResult as? Result.Success)?.data)
            Assert.assertTrue(result is Result.Success)
            Assert.assertTrue(fetchAfterDeleteResult is Result.Error)
        }
    }

    @Test
    fun fetchTransactionRecord_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            // act
            val result = dataSource.fetchTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionRecord_when_expired_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(0L)  // immediate expired
            }
            val data = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            dataSource.upsertTransactionRecord(data)
            // act
            val result = dataSource.fetchTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionRecord_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val data = listOf(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            dataSource.upsertTransactionRecord(data)
            // act
            val result = dataSource.fetchTransactionRecord()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun fetchTransactionRecord_given_id_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            // act
            val result = dataSource.fetchTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionRecord_given_id_when_expired_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(0L)  // immediate expired
            }
            val id = "test"
            val data = TransactionRecord(
                id = id,
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            dataSource.upsertTransactionRecord(data)
            // act
            val result = dataSource.fetchTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionRecord_given_id_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            val data = TransactionRecord(
                id = id,
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            dataSource.upsertTransactionRecord(data)
            // act
            val result = dataSource.fetchTransactionRecord(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }

    @Test
    fun upsertTransactionRecord_given_data_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val param = TransactionRecord(
                transactionDate = 0L,
                transactionType = TransactionType.INCOME,
                value = 0.0
            )
            // act
            val result = dataSource.upsertTransactionRecord(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun upsertTransactionRecord_given_listData_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionRecordCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val param = listOf(
                TransactionRecord(
                    transactionDate = 0L,
                    transactionType = TransactionType.INCOME,
                    value = 0.0
                )
            )
            // act
            val result = dataSource.upsertTransactionRecord(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

}