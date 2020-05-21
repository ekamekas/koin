package com.github.ekamekas.koin.transaction.data.source.transaction_record

import androidx.room.*

/**
 * Contract defined for transaction record data access object
 */
@Dao
interface TransactionRecordDAO {

    @Query("DELETE FROM ${TransactionRecordDB.TABLE_NAME}")
    suspend fun delete(): Int

    @Query("DELETE FROM ${TransactionRecordDB.TABLE_NAME} WHERE ${TransactionRecordDB.ID} = :id")
    suspend fun delete(id: String): Int

    @Query("SELECT * FROM ${TransactionRecordDB.TABLE_NAME}")
    suspend fun fetch(): List<TransactionRecordDB>

    @Query("SELECT * FROM ${TransactionRecordDB.TABLE_NAME} WHERE ${TransactionRecordDB.ID} = :id")
    suspend fun fetch(id: String): TransactionRecordDB

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: TransactionRecordDB): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: List<TransactionRecordDB>): List<Long>

    @Update
    suspend fun update(data: TransactionRecordDB): Int

    @Update
    suspend fun update(data: List<TransactionRecordDB>): Int

    @Transaction
    suspend fun upsert(data: TransactionRecordDB): Int {
        val insertResult = insert(data)
        return if(insertResult == -1L) {
            update(data)
        } else {
            1
        }
    }

    @Transaction
    suspend fun upsert(data: List<TransactionRecordDB>): Int {
        val insertResult = insert(data)
        val updateData = data.filterIndexed { index, _ -> insertResult[index] == -1L }
        val updateResult = update(updateData)
        return insertResult.size + updateResult
    }

}