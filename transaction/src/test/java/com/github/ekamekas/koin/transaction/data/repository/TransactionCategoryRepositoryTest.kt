package com.github.ekamekas.koin.transaction.data.repository

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.data.source.ITransactionDataSource
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
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
 * Testing suite for [TransactionCategoryRepository]
 */
@ExperimentalCoroutinesApi
class TransactionCategoryRepositoryTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    lateinit var transactionCategoryCacheDataSource: ITransactionDataSource.TransactionCategory
    @Mock
    lateinit var transactionCategoryLocalDataSource: ITransactionDataSource.TransactionCategory
    @Mock
    lateinit var transactionCategoryRemoteDataSource: ITransactionDataSource.TransactionCategory

    private lateinit var repository: ITransactionRepository.TransactionCategory

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = TransactionCategoryRepository(
            dispatcher = coroutineRule.dispatcher,
            transactionCategoryCacheDataSource = transactionCategoryCacheDataSource,
            transactionCategoryLocalDataSource = transactionCategoryLocalDataSource,
            transactionCategoryRemoteDataSource = transactionCategoryRemoteDataSource
        )
    }

    @Test
    fun deleteTransactionCategory_when_localReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_when_localReturnSuccess_then_invokeCacheOnce() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategory())
                .thenReturn(Result.Success(null))
            // act
            repository.deleteTransactionCategory()
            // assert
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).deleteTransactionCategory()
        }
    }
    @Test
    fun deleteTransactionCategory_when_localReturnSuccessAndCacheReturnError_then_returnSuccess() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategory())
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.deleteTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionCategory_when_localReturnSuccessAndCacheReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategory())
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.deleteTransactionCategory())
                .thenReturn(Result.Success(null))
            // act
            val result = repository.deleteTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id__when_localReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id_when_localReturnSuccess_then_invokeCacheOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategory(id))
                .thenReturn(Result.Success(null))
            // act
            repository.deleteTransactionCategory(id)
            // assert
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).deleteTransactionCategory(id)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id_when_localReturnSuccessAndCacheReturnError_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategory(id))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.deleteTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id_when_localReturnSuccessAndCacheReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategory(id))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.deleteTransactionCategory(id))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.deleteTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun deleteTransactionCategoryByCategory_given_id__when_localReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategoryByCategory_given_id_when_localReturnSuccess_then_invokeCacheOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategoryByCategory(id))
                .thenReturn(Result.Success(null))
            // act
            repository.deleteTransactionCategoryByCategory(id)
            // assert
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).deleteTransactionCategoryByCategory(id)
        }
    }
    @Test
    fun deleteTransactionCategoryByCategory_given_id_when_localReturnSuccessAndCacheReturnError_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategoryByCategory(id))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.deleteTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.deleteTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun deleteTransactionCategoryByCategory_given_id_when_localReturnSuccessAndCacheReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito
                .`when`(transactionCategoryLocalDataSource.deleteTransactionCategoryByCategory(id))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.deleteTransactionCategoryByCategory(id))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.deleteTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun getTransactionCategory_when_cacheReturnSuccess_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory())
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun getTransactionCategory_when_cacheReturnError_then_invokeLocalOnce() {
        runBlockingTest {
            // arrange
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            repository.getTransactionCategory()
            // assert
            Mockito.verify(transactionCategoryLocalDataSource, Mockito.times(1)).fetchTransactionCategory()
        }
    }
    @Test
    fun getTransactionCategory_when_cacheReturnErrorAndLocalReturnSuccess_then_returnSuccessAndDataAndInvokeCacheOnce() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory())
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategory_when_cacheReturnErrorAndLocalReturnError_then_invokeRemoteOnce() {
        runBlockingTest {
            // arrange
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            repository.getTransactionCategory()
            // assert
            Mockito.verify(transactionCategoryRemoteDataSource, Mockito.times(1)).fetchTransactionCategory()
        }
    }
    @Test
    fun getTransactionCategory_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnSuccess_then_returnSuccessAndDataAndInvokeLocalOnce() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategory())
                .thenReturn(Result.Success(data))
            Mockito.`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.getTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryLocalDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategory_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnSuccessAndPutLocalReturnSuccess_then_returnSuccessAndDataAndInvokeCacheOnce() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategory())
                .thenReturn(Result.Success(data))
            Mockito.`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.getTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategory_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategory())
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.getTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun getTransactionCategory_given_id_when_cacheReturnSuccess_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = TransactionCategory(
                id = id,
                name = "test",
                image = null
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun getTransactionCategory_given_id_when_cacheReturnError_then_invokeLocalOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            repository.getTransactionCategory(id)
            // assert
            Mockito.verify(transactionCategoryLocalDataSource, Mockito.times(1)).fetchTransactionCategory(id)
        }
    }
    @Test
    fun getTransactionCategory_given_id_when_cacheReturnErrorAndLocalReturnSuccess_then_returnSuccessAndDataAndInvokeCacheOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = TransactionCategory(
                id = id,
                name = "test",
                image = null
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategory_given_id_when_cacheReturnErrorAndLocalReturnError_then_invokeRemoteOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            repository.getTransactionCategory(id)
            // assert
            Mockito.verify(transactionCategoryRemoteDataSource, Mockito.times(1)).fetchTransactionCategory(id)
        }
    }
    @Test
    fun getTransactionCategory_given_id_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnSuccess_then_returnSuccessAndDataAndInvokeLocalOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = TransactionCategory(
                id = id,
                name = "test",
                image = null
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Success(data))
            Mockito.`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.getTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryLocalDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategory_given_id_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnSuccessAndPutLocalReturnSuccess_then_returnSuccessAndDataAndInvokeCacheOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = TransactionCategory(
                id = id,
                name = "test",
                image = null
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Success(data))
            Mockito.`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.getTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategory_given_id_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.getTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }

    @Test
    fun getTransactionCategoryByCategory_given_id_when_cacheReturnSuccess_then_returnSuccessAndData() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = listOf(
                TransactionCategory(
                    id = id,
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun getTransactionCategoryByCategory_given_id_when_cacheReturnError_then_invokeLocalOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            repository.getTransactionCategoryByCategory(id)
            // assert
            Mockito.verify(transactionCategoryLocalDataSource, Mockito.times(1)).fetchTransactionCategoryByCategory(id)
        }
    }
    @Test
    fun getTransactionCategoryByCategory_given_id_when_cacheReturnErrorAndLocalReturnSuccess_then_returnSuccessAndDataAndInvokeCacheOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = listOf(
                TransactionCategory(
                    id = id,
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Success(data))
            // act
            val result = repository.getTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategoryByCategory_given_id_when_cacheReturnErrorAndLocalReturnError_then_invokeRemoteOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            repository.getTransactionCategoryByCategory(id)
            // assert
            Mockito.verify(transactionCategoryRemoteDataSource, Mockito.times(1)).fetchTransactionCategoryByCategory(id)
        }
    }
    @Test
    fun getTransactionCategoryByCategory_given_id_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnSuccess_then_returnSuccessAndDataAndInvokeLocalOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = listOf(
                TransactionCategory(
                    id = id,
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Success(data))
            Mockito.`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.getTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryLocalDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategoryByCategory_given_id_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnSuccessAndPutLocalReturnSuccess_then_returnSuccessAndDataAndInvokeCacheOnce() {
        runBlockingTest {
            // arrange
            val id = "test"
            val data = listOf(
                TransactionCategory(
                    id = id,
                    name = "test",
                    image = null
                )
            )
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Success(data))
            Mockito.`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.getTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun getTransactionCategoryByCategory_given_id_when_cacheReturnErrorAndLocalReturnErrorAndRemoteReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val id = "test"
            Mockito.`when`(transactionCategoryCacheDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryLocalDataSource.fetchTransactionCategoryByCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            Mockito.`when`(transactionCategoryRemoteDataSource.fetchTransactionCategory(id))
                .thenReturn(Result.Error(RuntimeException("expected error")))
            // act
            val result = repository.getTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }

    @Test
    fun putTransactionCategory_given_data_when_localReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val data = TransactionCategory(
                name = "test",
                image = null
            )
            Mockito
                .`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Error(RuntimeException("expected behavior")))
            // act
            val result = repository.putTransactionCategory(data)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun putTransactionCategory_given_data_when_localReturnSuccess_then_invokeCacheOnce() {
        runBlockingTest {
            // arrange
            val data = TransactionCategory(
                name = "test",
                image = null
            )
            Mockito
                .`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            // act
            repository.putTransactionCategory(data)
            // assert
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun putTransactionCategory_given_data_when_localReturnSuccessAndCacheReturnError_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val data = TransactionCategory(
                name = "test",
                image = null
            )
            Mockito
                .`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Error(RuntimeException("expected behavior")))
            // act
            val result = repository.putTransactionCategory(data)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun putTransactionCategory_given_data_when_localReturnSuccessAndCacheReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val data = TransactionCategory(
                name = "test",
                image = null
            )
            Mockito
                .`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.putTransactionCategory(data)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

    @Test
    fun putTransactionCategory_given_listData_when_localReturnError_then_returnError() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito
                .`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Error(RuntimeException("expected behavior")))
            // act
            val result = repository.putTransactionCategory(data)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun putTransactionCategory_given_listData_when_localReturnSuccess_then_invokeCacheOnce() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito
                .`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            // act
            repository.putTransactionCategory(data)
            // assert
            Mockito.verify(transactionCategoryCacheDataSource, Mockito.times(1)).upsertTransactionCategory(data)
        }
    }
    @Test
    fun putTransactionCategory_given_listData_when_localReturnSuccessAndCacheReturnError_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito
                .`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Error(RuntimeException("expected behavior")))
            // act
            val result = repository.putTransactionCategory(data)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun putTransactionCategory_given_listData_when_localReturnSuccessAndCacheReturnSuccess_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            Mockito
                .`when`(transactionCategoryLocalDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            Mockito
                .`when`(transactionCategoryCacheDataSource.upsertTransactionCategory(data))
                .thenReturn(Result.Success(null))
            // act
            val result = repository.putTransactionCategory(data)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

}