package com.github.ekamekas.koin.transaction.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.ekamekas.koin.transaction.data.source.transaction_category.TransactionCategoryDAO
import com.github.ekamekas.koin.transaction.data.source.transaction_category.TransactionCategoryDB
import com.github.ekamekas.koin.transaction.data.source.transaction_record.TransactionRecordDAO
import com.github.ekamekas.koin.transaction.data.source.transaction_record.TransactionRecordDB

/**
 * Database for transaction module
 */
@Database(
    entities = [
        TransactionRecordDB::class,
        TransactionCategoryDB::class
    ],
    version = 2
)
abstract class TransactionDatabase: RoomDatabase() {

    abstract fun transactionRecordDAO(): TransactionRecordDAO
    abstract fun transactionCategoryDAO(): TransactionCategoryDAO

}

// database migration format will be MIGRATION_{START}_{END}
val MIGRATION_1_2 = object: Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.beginTransaction()

        // create transaction category table
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `transaction_category`
            (
                `id` TEXT NOT NULL PRIMARY KEY,
                `name` TEXT NOT NULL,
                `description` TEXT DEFAULT NULL,
                `image` TEXT DEFAULT NULL,
                `parentCategoryId` TEXT DEFAULT NULL,
                FOREIGN KEY (`parentCategoryId`)
                    REFERENCES `transaction_category`(`id`)
                        ON DELETE CASCADE
                        ON UPDATE NO ACTION
            )
        """.trimIndent())

        // create transaction category index
        database.execSQL("""
            CREATE INDEX IF NOT EXISTS `index_transaction_category_parentCategoryId` 
                ON `transaction_category` (`parentCategoryId`)
        """.trimIndent())

        // create transaction category column on transaction record and set as null
        // 1. rename table
        // 2. create table with new metadata
        // 3. copy old table to the new table
        // 4. drop old table
        database.execSQL("""
            ALTER TABLE `transaction_record` RENAME TO `transaction_record_old`
        """.trimIndent())
        database.execSQL("""
            CREATE TABLE `transaction_record`
            (
                `id` TEXT NOT NULL PRIMARY KEY,
                `description` TEXT DEFAULT NULL,
                `transactionDate` INTEGER NOT NULL,
                `transactionType` TEXT NOT NULL,
                `value` REAL NOT NULL,
                `transactionCategoryId` TEXT DEFAULT NULL,
                FOREIGN KEY (`transactionCategoryId`)
                    REFERENCES `transaction_category`(`id`)
                        ON DELETE SET NULL
                        ON UPDATE NO ACTION
            )
        """.trimIndent())
        database.execSQL("""
            INSERT INTO `transaction_record` (`id`,`description`,`transactionDate`,`transactionType`,`value`)
                SELECT `id`,`description`,`transactionDate`,`transactionType`,`value`
                    FROM `transaction_record_old`
        """.trimIndent())
        database.execSQL("""
            DROP TABLE `transaction_record_old`
        """.trimIndent())

        // create transaction record index
        database.execSQL("""
            CREATE INDEX IF NOT EXISTS `index_transaction_record_transactionCategoryId`
                ON `transaction_record` (`transactionCategoryId`)
        """.trimIndent())

        database.setTransactionSuccessful()
        database.endTransaction()
    }
}