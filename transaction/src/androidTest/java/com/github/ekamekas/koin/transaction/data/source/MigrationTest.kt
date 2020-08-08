package com.github.ekamekas.koin.transaction.data.source

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.github.ekamekas.koin.transaction.BuildConfig
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.*

/**
 * Testing suite for database migration
 */
class MigrationTest {

    private val DB_NAME = BuildConfig.LIBRARY_PACKAGE_NAME

    @get: Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TransactionDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun when_migrateVersionFrom1To2_then_mustBeSuccess() {
        // arrange
        val id = UUID.randomUUID()
        val description = "testing purpose"
        val date = System.currentTimeMillis()
        val type = "TEST"
        val value = 10.0
        // act
        helper.createDatabase(DB_NAME, 1).apply {
            // insert some data
            execSQL("""
                INSERT INTO `transaction_record` (`id`,`description`,`transactionDate`,`transactionType`,`value`)
                VALUES
                ('$id', '$description', $date, '$type', $value)
            """.trimIndent())

            // close to increase version
            close()
        }

        val db = helper.runMigrationsAndValidate(DB_NAME, 2, true, MIGRATION_1_2)
        val result = db.query("SELECT * FROM `transaction_record` WHERE `id`='$id'")
        // assert
        Assert.assertEquals(1, result.count)
    }

}