package com.github.ekamekas.koin.transaction.data.source.transaction_category

import androidx.room.*

/**
 * Contract defined for transaction category data access object
 */
@Dao
interface TransactionCategoryDAO {

    @Query("DELETE FROM ${TransactionCategoryDB.TABLE_NAME}")
    suspend fun delete(): Int

    @Query("DELETE FROM ${TransactionCategoryDB.TABLE_NAME} WHERE ${TransactionCategoryDB.ID} = :id")
    suspend fun delete(id: String): Int

    @Transaction
    @Query("SELECT * FROM ${TransactionCategoryDB.TABLE_NAME}")
    suspend fun fetch(): List<TransactionCategoryAndParentDB>

    @Transaction
    @Query("SELECT * FROM ${TransactionCategoryDB.TABLE_NAME} WHERE ${TransactionCategoryDB.ID} = :id")
    suspend fun fetch(id: String): TransactionCategoryAndParentDB

    @Transaction
    @Query("SELECT * FROM ${TransactionCategoryDB.TABLE_NAME} WHERE ${TransactionCategoryDB.ID} = :id OR ${TransactionCategoryDB.PARENT_CATEGORY_ID} = :id")
    suspend fun fetchByCategory(id: String): List<TransactionCategoryAndParentDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: TransactionCategoryDB): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: List<TransactionCategoryDB>): List<Long>

    @Update
    suspend fun update(data: TransactionCategoryDB): Int

    @Update
    suspend fun update(data: List<TransactionCategoryDB>): Int

    @Transaction
    suspend fun upsert(data: TransactionCategoryDB): Int {
        val insertResult = insert(data)
        return if(insertResult == -1L) {
            update(data)
        } else {
            1
        }
    }

    @Transaction
    suspend fun upsert(data: List<TransactionCategoryDB>): Int {
        val insertResult = insert(data)
        val updateData = data.filterIndexed { index, _ -> insertResult[index] == -1L }
        val updateResult = update(updateData)
        return insertResult.size + updateResult
    }

}