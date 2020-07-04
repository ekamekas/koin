package com.github.ekamekas.koin.transaction.data.source.transaction_category

import com.github.ekamekas.baha.core.domain.entity.Result
import com.github.ekamekas.koin.transaction.CoroutineTestRule
import com.github.ekamekas.koin.transaction.domain.entity.TransactionCategory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Testing suite for [TransactionCategoryCacheDataSource]
 */
@ExperimentalCoroutinesApi
class TransactionCategoryCacheDataSourceTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    private val expirationOffsetMillis = 20000L  // 20s

    @Test
    fun deleteTransactionCategory_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            // act
            val result = dataSource.deleteTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            dataSource.upsertTransactionCategory(data)
            // act
            val fetchBeforeDeleteResult = dataSource.fetchTransactionCategory()
            val result = dataSource.deleteTransactionCategory()
            val fetchAfterDeleteResult = dataSource.fetchTransactionCategory()
            // assert
            Assert.assertTrue(fetchBeforeDeleteResult is Result.Success)
            Assert.assertEquals(data.size, (fetchBeforeDeleteResult as? Result.Success)?.data?.size)
            Assert.assertTrue(result is Result.Success)
            Assert.assertTrue(fetchAfterDeleteResult is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            // act
            val result = dataSource.deleteTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategory_given_id_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            val data = TransactionCategory(
                id = id,
                name = "test",
                image = null
            )
            dataSource.upsertTransactionCategory(data)
            // act
            val fetchBeforeDeleteResult = dataSource.fetchTransactionCategory(id)
            val result = dataSource.deleteTransactionCategory(id)
            val fetchAfterDeleteResult = dataSource.fetchTransactionCategory(id)
            // assert
            Assert.assertTrue(fetchBeforeDeleteResult is Result.Success)
            Assert.assertEquals(data, (fetchBeforeDeleteResult as? Result.Success)?.data)
            Assert.assertTrue(result is Result.Success)
            Assert.assertTrue(fetchAfterDeleteResult is Result.Error)
        }
    }

    @Test
    fun deleteTransactionCategoryByCategory_given_id_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            // act
            val result = dataSource.deleteTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun deleteTransactionCategoryByCategory_given_id_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val parentCategory = TransactionCategory(
                name = "parent",
                image = null
            )
            val dataWithoutSameCategory = listOf(
                TransactionCategory(
                    name = "otherParent",
                    image = null
                )
            )
            val data = dataWithoutSameCategory.plus(
                listOf(
                    parentCategory,
                    TransactionCategory(
                        name = "test",
                        image = null,
                        parentCategory = parentCategory
                    )
                )
            )
            dataSource.upsertTransactionCategory(data)
            // act
            val fetchBeforeDeleteResult = dataSource.fetchTransactionCategory()
            val result = dataSource.deleteTransactionCategoryByCategory(parentCategory.id)
            val fetchAfterDeleteResult = dataSource.fetchTransactionCategory()
            // assert
            Assert.assertTrue(fetchBeforeDeleteResult is Result.Success)
            Assert.assertEquals(data.sortedBy { it.id }, (fetchBeforeDeleteResult as? Result.Success)?.data?.sortedBy { it.id })
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(dataWithoutSameCategory, (fetchAfterDeleteResult as? Result.Success)?.data)
        }
    }

    @Test
    fun fetchTransactionCategory_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            // act
            val result = dataSource.fetchTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategory_when_expired_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(0L)  // immediate expired
            }
            val data = TransactionCategory(
                name = "test",
                image = null
            )
            dataSource.upsertTransactionCategory(data)
            delay(1000)  // 1s
            // act
            val result = dataSource.fetchTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategory_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val data = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            dataSource.upsertTransactionCategory(data)
            // act
            val result = dataSource.fetchTransactionCategory()
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }
    @Test
    fun fetchTransactionCategory_given_id_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            // act
            val result = dataSource.fetchTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategory_given_id_when_expired_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(0L)  // immediate expired
            }
            val id = "test"
            val data = TransactionCategory(
                name = "test",
                image = null
            )
            dataSource.upsertTransactionCategory(data)
            delay(1000)  // 1s
            // act
            val result = dataSource.fetchTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategory_given_id_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            val data = TransactionCategory(
                id = id,
                name = "test",
                image = null
            )
            dataSource.upsertTransactionCategory(data)
            // act
            val result = dataSource.fetchTransactionCategory(id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(data, (result as? Result.Success)?.data)
        }
    }

    @Test
    fun fetchTransactionCategoryByCategory_given_id_when_cacheNotHit_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val id = "test"
            // act
            val result = dataSource.fetchTransactionCategoryByCategory(id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategoryByCategory_given_id_when_expired_then_returnError() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(0L)  // immediate expired
            }
            val parentCategory = TransactionCategory(
                name = "parent",
                image = null
            )
            val dataWithSameCategory = listOf(
                parentCategory,
                TransactionCategory(
                    name = "otherParent",
                    image = null,
                    parentCategory = parentCategory
                )
            )
            val data = dataWithSameCategory.plus(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            dataSource.upsertTransactionCategory(data)
            delay(1000)  // 1s
            // act
            val result = dataSource.fetchTransactionCategory(parentCategory.id)
            // assert
            Assert.assertTrue(result is Result.Error)
        }
    }
    @Test
    fun fetchTransactionCategoryByCategory_given_id_when_cacheHit_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val parentCategory = TransactionCategory(
                name = "parent",
                image = null
            )
            val dataWithSameCategory = listOf(
                parentCategory,
                TransactionCategory(
                    name = "child",
                    image = null,
                    parentCategory = parentCategory
                )
            )
            val data = dataWithSameCategory.plus(
                TransactionCategory(
                    name = "otherParent",
                    image = null
                )
            )
            dataSource.upsertTransactionCategory(data)
            // act
            val result = dataSource.fetchTransactionCategoryByCategory(parentCategory.id)
            // assert
            Assert.assertTrue(result is Result.Success)
            Assert.assertEquals(dataWithSameCategory.sortedBy { it.id }, (result as? Result.Success)?.data?.sortedBy { it.id })
        }
    }

    @Test
    fun upsertTransactionCategory_given_data_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val param = TransactionCategory(
                name = "test",
                image = null
            )
            // act
            val result = dataSource.upsertTransactionCategory(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }
    @Test
    fun upsertTransactionCategory_given_listData_then_returnSuccess() {
        runBlockingTest {
            // arrange
            val dataSource = TransactionCategoryCacheDataSource(
                dispatcher = coroutineRule.dispatcher
            ).apply {
                setExpirationOffsetMillis(expirationOffsetMillis)
            }
            val param = listOf(
                TransactionCategory(
                    name = "test",
                    image = null
                )
            )
            // act
            val result = dataSource.upsertTransactionCategory(param)
            // assert
            Assert.assertTrue(result is Result.Success)
        }
    }

}