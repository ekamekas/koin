package com.github.ekamekas.koin.transaction.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.ekamekas.koin.transaction.data.source.transaction_record.TransactionRecordDAO
import com.github.ekamekas.koin.transaction.data.source.transaction_record.TransactionRecordDB

/**
 * Database for transaction module
 */
@Database(
    entities = [
        TransactionRecordDB::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TransactionDatabase: RoomDatabase() {

    abstract fun transactionRecordDAO(): TransactionRecordDAO

}