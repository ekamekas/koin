package com.github.ekamekas.koin.transaction.data.source

import android.content.Context
import androidx.room.Room
import com.github.ekamekas.baha.core.di.CacheDataSource
import com.github.ekamekas.baha.core.di.LocalDataSource
import com.github.ekamekas.koin.transaction.BuildConfig
import com.github.ekamekas.koin.transaction.data.source.transaction_record.TransactionRecordCacheDataSource
import com.github.ekamekas.koin.transaction.data.source.transaction_record.TransactionRecordLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        LocalDataSourceModule::class
    ]
)
abstract class DataSourceModule {

    // transaction record
    @Singleton
    @Binds
    @CacheDataSource
    abstract fun bindTransactionRecordCacheDataSource(dataSource: TransactionRecordCacheDataSource): ITransactionDataSource.TransactionRecord
    @Singleton
    @Binds
    @LocalDataSource
    abstract fun bindTransactionRecordLocalDataSource(dataSource: TransactionRecordLocalDataSource): ITransactionDataSource.TransactionRecord

}

@Module
object LocalDataSourceModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ): TransactionDatabase {
        return Room
            .databaseBuilder(
                context,
                TransactionDatabase::class.java,
                BuildConfig.LIBRARY_PACKAGE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

}